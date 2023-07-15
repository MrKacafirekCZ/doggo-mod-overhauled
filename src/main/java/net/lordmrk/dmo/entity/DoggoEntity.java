package net.lordmrk.dmo.entity;

import com.google.common.collect.Sets;
import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoFeeling;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.TrackedDoggoData;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.lordmrk.dmo.entity.ai.goal.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class DoggoEntity extends TameableEntity implements Angerable {

    private static final TrackedData<DoggoAction> ACTION;
    private static final TrackedData<Boolean> ACTION_TICKING;
    private static final TrackedData<Integer> ANGER_TIME;
    private static final TrackedData<Boolean> BEGGING;
    private static final TrackedData<BlockPos> BOWL_POS;
    private static final TrackedData<Integer> COLLAR_COLOR;
    private static final TrackedData<DoggoFeeling> FEELING;
    private static final TrackedData<Boolean> MOUTH_OPENED;
    private static final TrackedData<ItemStack> MOUTH_STACK;
    private static final TrackedData<Integer> SCRATCHING_SIDE;
    private static final Set<Block> diggableBlocks = Sets.newHashSet(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.SAND, Blocks.RED_SAND, Blocks.GRAVEL, Blocks.SNOW_BLOCK);
    private float begAnimationProgress;
    private float lastBegAnimationProgress;
    private boolean furWet;
    private boolean canShakeWaterOff;
    private float shakeProgress;
    private float lastShakeProgress;
    private static final UniformIntProvider ANGER_TIME_RANGE;
    @Nullable
    private UUID angryAt;

    public static final Predicate<ItemEntity> PICKABLE_DROP_FILTER;
    public static final Predicate<Entity> FOLLOWABLE_DROP_FILTER;
    public static final double DEFAULT_DISTANCE_FROM_DOGGO = 36.0D;

    //Client sided only
    private float animationTick;

    //Server sided only
    private int actionDelay;
    private boolean damaged;
    private boolean justStretched;
    private int mouthOpenedTick;
    private boolean readyToPlay = false;

    public DoggoEntity(EntityType<? extends DoggoEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(true);
        this.setSitting(true);
        this.setInSittingPose(true);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0F);
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.hasAngerTime() && super.canBeLeashedBy(player);
    }

    public boolean canStartDigging() {
        return diggableBlocks.contains(this.getWorld().getBlockState(this.getBlockPos().down()).getBlock());
    }

    public static DefaultAttributeContainer.Builder createDoggoAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DoggoEntity doggoEntity = DoggoModOverhauled.DOGGO.create(world);
        UUID uuid = this.getOwnerUuid();
        if (uuid != null) {
            doggoEntity.setOwnerUuid(uuid);
        }

        return doggoEntity;
    }

    public boolean damage(DamageSource source, float amount) {
        damaged = true;
        dropStackInMouth();
        return super.damage(source, amount);
    }

    public void dropStackInMouth() {
        if(hasStackInMouth()) {
            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY(), this.getZ(), getStackInMouth());
            setStackInMouth(null);
        }
    }

    public int getActionDelay() {
        return actionDelay;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getAction() == DoggoAction.NAPPING) {
            return null;
        }

        if (this.hasAngerTime()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        }

        if (this.random.nextInt(2) == 0) {
            return null;
        }

        if (this.isTamed() && this.random.nextInt(3) == 0) {
            if(this.getHealth() < 10.0F) {
                return SoundEvents.ENTITY_WOLF_WHINE;
            }

            if(!this.hasStackInMouth() && !this.getWorld().isClient) {
                this.setMouthOpened(true);
                return SoundEvents.ENTITY_WOLF_PANT;
            }
        }

        return SoundEvents.ENTITY_WOLF_AMBIENT;
    }

    public float getAnimationTick() {
        return animationTick;
    }

    public DoggoAction getAction() {
        return this.dataTracker.get(ACTION);
    }

    public float getBegAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastBegAnimationProgress, this.begAnimationProgress) * 0.15F * 3.1415927F;
    }

    public BlockPos getBowlPos() {
        return this.dataTracker.get(BOWL_POS);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataTracker.get(COLLAR_COLOR));
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    public DoggoFeeling getFeeling() {
        return this.dataTracker.get(FEELING);
    }

    public float getFurWetBrightnessMultiplier(float tickDelta) {
        return Math.min(0.5F + MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) / 2.0F * 0.5F, 1.0F);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    public int getScratchingSide() {
        return this.dataTracker.get(SCRATCHING_SIDE);
    }

    public float getShakeAnimationProgress(float tickDelta, float f) {
        float g = (MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) + f) / 1.8F;
        if (g < 0.0F) {
            g = 0.0F;
        } else if (g > 1.0F) {
            g = 1.0F;
        }

        return MathHelper.sin(g * 3.1415927F) * MathHelper.sin(g * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public ItemStack getStackInMouth() {
        return this.dataTracker.get(MOUTH_STACK);
    }

    public float getTailAngle() {
        if (this.hasAngerTime()) {
            return 1.5393804F;
        } else {
            return this.isTamed() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * 3.1415927F : 0.62831855F;
        }
    }

    public void handleStatus(byte status) {
        if (status == 8) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0F;
            this.lastShakeProgress = 0.0F;
        } else if (status == 56) {
            this.resetShake();
        } else {
            super.handleStatus(status);
        }
    }

    public boolean hasBeenDamaged() {
        return damaged;
    }

    public boolean hasJustStretched() {
        return justStretched;
    }

    public boolean hasMouthOpened() {
        return this.dataTracker.get(MOUTH_OPENED);
    }

    public boolean hasStackInMouth() {
        return getStackInMouth() != null && getStackInMouth().getItem() != Items.AIR;
    }

    public boolean hasStackInMouth(ItemStack item) {
        return hasStackInMouth() && getStackInMouth() == item;
    }

    public boolean hasStackInMouth(Item item) {
        return hasStackInMouth() && getStackInMouth().getItem() == item;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if (this.getWorld().isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.BONE) && !this.isTamed() && !this.hasAngerTime();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }

        if (this.isTamed()) {
            if(!this.isBaby() && !this.hasStackInMouth() && isAction(DoggoAction.NEUTRAL)) {
                if(item.isFood() && item.getFoodComponent().isMeat()) {
                    setStackInMouth(itemStack.split(1));
                    return ActionResult.SUCCESS;
                }
            }

            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                this.heal((float)item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            }

            if (!(item instanceof DyeItem)) {
                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResult.SUCCESS;
                }

                return actionResult;
            }

            DyeColor dyeColor = ((DyeItem)item).getColor();
            if (dyeColor != this.getCollarColor()) {
                this.setCollarColor(dyeColor);
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    public boolean isFurWet() {
        return this.furWet;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTION, DoggoAction.NEUTRAL);
        this.dataTracker.startTracking(ACTION_TICKING, false);
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(BEGGING, false);
        this.dataTracker.startTracking(BOWL_POS, new BlockPos(0, 0, 0));
        this.dataTracker.startTracking(COLLAR_COLOR, DyeColor.RED.getId());
        this.dataTracker.startTracking(FEELING, DoggoFeeling.NEUTRAL);
        this.dataTracker.startTracking(MOUTH_OPENED, false);
        this.dataTracker.startTracking(MOUTH_STACK, new ItemStack(Items.AIR));
        this.dataTracker.startTracking(SCRATCHING_SIDE, 0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new DoggoNapGoal(this));
        this.goalSelector.add(3, new DoggoListeningGoal(this));
        this.goalSelector.add(3, new DoggoScratchGoal(this));
        this.goalSelector.add(4, new DoggoEatGoal(this));
        this.goalSelector.add(5, new SitGoal(this));
        this.goalSelector.add(6, new DoggoEatFromBowlGoal(this));
        this.goalSelector.add(7, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(8, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(9, new DoggoBegGoal(this, 8.0F));
        this.goalSelector.add(10, new DoggoPlayTennisBallGoal(this));
        this.goalSelector.add(10, new DoggoReadyPlayTimeGoal(this));
        this.goalSelector.add(10, new DoggoSniffingGoal(this));
        this.goalSelector.add(10, new DoggoStretchGoal(this));
        this.goalSelector.add(11, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(12, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(13, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(13, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        //this.targetSelector.add(4, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        //this.targetSelector.add(5, new UntamedActiveTargetGoal(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
        this.targetSelector.add(6, new UntamedActiveTargetGoal(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.targetSelector.add(7, new ActiveTargetGoal(this, AbstractSkeletonEntity.class, false));
        this.targetSelector.add(8, new UniversalAngerGoal(this, true));

        //this.goalSelector.add(1, new WolfEntity.WolfEscapeDangerGoal(1.5D));
        //this.goalSelector.add(3, new WolfEntity.AvoidLlamaGoal(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
        //this.goalSelector.add(7, new AnimalMateGoal(this, 1.0D));
    }

    public boolean isAction(DoggoAction doggoAction) {
        return getAction() == doggoAction;
    }

    public boolean isActionTicking() {
        return this.dataTracker.get(ACTION_TICKING);
    }

    public boolean isBegging() {
        return this.dataTracker.get(BEGGING);
    }

    public boolean isEntityClose(Entity entity) {
        return isEntityClose(entity, DEFAULT_DISTANCE_FROM_DOGGO);
    }

    public boolean isEntityClose(Entity entity, double distance) {
        return !entity.isSpectator() && this.squaredDistanceTo(entity) < distance;
    }

    public boolean isOwnerClose() {
        return isOwnerClose(DEFAULT_DISTANCE_FROM_DOGGO);
    }

    public boolean isOwnerClose(double distance) {
        LivingEntity livingEntity = this.getOwner();

        if(livingEntity == null) {
            return false;
        }

        return isEntityClose(livingEntity, distance);
    }

    public boolean isReadyToPlay() {
        return readyToPlay;
    }

    public boolean isStackInMouthMeat() {
        return hasStackInMouth() && getStackInMouth().isFood() && getStackInMouth().getItem().getFoodComponent().isMeat();
    }

    public void onDeath(DamageSource damageSource) {
        this.furWet = false;
        this.canShakeWaterOff = false;
        this.lastShakeProgress = 0.0F;
        this.shakeProgress = 0.0F;
        super.onDeath(damageSource);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }

        this.readAngerFromNbt(this.getWorld(), nbt);
    }

    private void resetShake() {
        this.canShakeWaterOff = false;
        this.shakeProgress = 0.0F;
        this.lastShakeProgress = 0.0F;
    }

    public void setAction(DoggoAction doggoAction) {
        this.dataTracker.set(ACTION, doggoAction);
    }

    public void setActionTicking(boolean actionTicking) {
        this.dataTracker.set(ACTION_TICKING, actionTicking);

        if(!actionTicking) {
            this.animationTick = 0;
        }
    }

    public void setBegging(boolean begging) {
        this.dataTracker.set(BEGGING, begging);
    }

    public void setBowlPos(BlockPos bowlPos) {
        this.dataTracker.set(BOWL_POS, bowlPos);
    }

    public void setCollarColor(DyeColor color) {
        this.dataTracker.set(COLLAR_COLOR, color.getId());
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public void setFeeling(DoggoFeeling doggoFeeling) {
        this.dataTracker.set(FEELING, doggoFeeling);
    }

    public void setJustStretched(boolean justStretched) {
        this.justStretched = justStretched;
    }

    public void setMouthOpened(boolean mouthOpened) {
        this.dataTracker.set(MOUTH_OPENED, mouthOpened);
    }

    public void setReadyToPlay(boolean readyToPlay) {
        this.readyToPlay = readyToPlay;
    }

    public void setScratchingSide(int scratchingSide) {
        this.dataTracker.set(SCRATCHING_SIDE, scratchingSide);
    }

    public void setStackInMouth(ItemStack itemStack) {
        if(itemStack == null) {
            itemStack = new ItemStack(Items.AIR);
        }

        this.dataTracker.set(MOUTH_STACK, itemStack);
    }

    public void startActionDelay() {
        actionDelay = 200 + this.random.nextInt(20) * 10;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isAlive()) {
            this.lastBegAnimationProgress = this.begAnimationProgress;
            if (this.isBegging()) {
                this.begAnimationProgress += (1.0F - this.begAnimationProgress) * 0.4F;
            } else {
                this.begAnimationProgress += (0.0F - this.begAnimationProgress) * 0.4F;
            }

            if(this.getAction() == DoggoAction.NEUTRAL) {
                if (this.isWet()) {
                    this.furWet = true;
                    if (this.canShakeWaterOff && !this.getWorld().isClient) {
                        this.getWorld().sendEntityStatus(this, (byte)56);
                        this.resetShake();
                    }
                } else if (this.furWet || this.canShakeWaterOff) {
                    if (this.shakeProgress == 0.0F) {
                        this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.emitGameEvent(GameEvent.ENTITY_SHAKE);
                    }

                    this.lastShakeProgress = this.shakeProgress;
                    this.shakeProgress += 0.05F;
                    if (this.lastShakeProgress >= 2.0F) {
                        this.furWet = false;
                        this.canShakeWaterOff = false;
                        this.lastShakeProgress = 0.0F;
                        this.shakeProgress = 0.0F;
                    }

                    if (this.shakeProgress > 0.4F) {
                        float f = (float)this.getY();
                        int i = (int)(MathHelper.sin((this.shakeProgress - 0.4F) * 3.1415927F) * 7.0F);
                        Vec3d vec3d = this.getVelocity();

                        for(int j = 0; j < i; ++j) {
                            float g = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                            float h = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                            this.getWorld().addParticle(ParticleTypes.SPLASH, this.getX() + (double)g, (double)(f + 0.8F), this.getZ() + (double)h, vec3d.x, vec3d.y, vec3d.z);
                        }
                    }
                }
            }
        }

        if(this.getWorld().isClient) {
            if(isActionTicking()) {
                this.animationTick = (this.animationTick + 0.5f) % 360;
            } else if(this.animationTick > 0) {
                this.animationTick = 0f;
            }

            double x = MathHelper.sin(-this.headYaw % 360 / 58) * 0.5f;
            double z = MathHelper.cos(this.headYaw % 360 / 58) * 0.5f;

            switch(getAction()) {
                case DIGGING:
                    this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.getWorld().getBlockState(this.getBlockPos().down())), this.getX() + x, this.getY(), this.getZ() + z, 0.1, 0.1, 0.1);
                    break;
                case EATING:
                    this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, getStackInMouth()), this.getX() + x * (1.6 + ((double) this.random.nextInt(5) / 10)), this.getY() + 0.55, this.getZ() + z * (1.6 + ((double) this.random.nextInt(5) / 10)), 0, 0, 0);
                    break;
                case EATING_FROM_BOWL:
                    if(getBowlPos() != null && this.getWorld().getBlockEntity(getBowlPos()) != null) {
                        ItemStack item = ((DogBowlEntity) this.getWorld().getBlockEntity(getBowlPos())).getStack(0);

                        if(item != null && !item.isEmpty()) {
                            this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, item), getBowlPos().getX() + 0.4 + ((double) this.random.nextInt(3) / 10), getBowlPos().getY() + 0.2, getBowlPos().getZ() + 0.4 + ((double) this.random.nextInt(3) / 10), 0, 0, 0);
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            if(actionDelay > 0) {
                actionDelay--;
            }

            if(hasMouthOpened()) {
                this.mouthOpenedTick++;

                if(this.mouthOpenedTick > 20) {
                    this.setMouthOpened(false);
                    this.mouthOpenedTick = 0;
                }
            }

            switch(getAction()) {
                case DIGGING:
                    if(this.age % 4 == 0) {
                        Block block = this.getWorld().getBlockState(this.getBlockPos().down()).getBlock();
                        SoundEvent sound = null;

                        if(block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
                            sound = SoundEvents.BLOCK_GRASS_HIT;
                        } else if(block == Blocks.SAND || block == Blocks.RED_SAND) {
                            sound = SoundEvents.BLOCK_SAND_HIT;
                        } else if(block == Blocks.GRAVEL) {
                            sound = SoundEvents.BLOCK_GRAVEL_HIT;
                        } else if(block == Blocks.SNOW_BLOCK) {
                            sound = SoundEvents.BLOCK_SNOW_HIT;
                        }

                        if(sound != null) {
                            this.getWorld().playSound(null,
                                    this.getX(), this.getY(), this.getZ(),
                                    sound, SoundCategory.NEUTRAL, 0.2f, 0f);
                        }
                    }
                    break;
            }
        }
    }

    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient && this.furWet && !this.canShakeWaterOff && !this.isNavigating() && this.isOnGround()) {
            this.canShakeWaterOff = true;
            this.shakeProgress = 0.0F;
            this.lastShakeProgress = 0.0F;
            this.getWorld().sendEntityStatus(this, (byte)8);
        }

        if (!this.getWorld().isClient) {
            this.tickAngerLogic((ServerWorld)this.getWorld(), true);
        }

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("CollarColor", (byte)this.getCollarColor().getId());
        this.writeAngerToNbt(nbt);
    }

    static {
        ACTION = DataTracker.registerData(DoggoEntity.class, TrackedDoggoData.DOGGO_ACTION);
        ACTION_TICKING = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        ANGER_TIME = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BEGGING = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOWL_POS = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
        COLLAR_COLOR = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.INTEGER);
        FEELING = DataTracker.registerData(DoggoEntity.class, TrackedDoggoData.DOGGO_FEELING);
        MOUTH_OPENED = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        MOUTH_STACK = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
        SCRATCHING_SIDE = DataTracker.registerData(DoggoEntity.class, TrackedDataHandlerRegistry.INTEGER);

        PICKABLE_DROP_FILTER = (itemEntity) -> !itemEntity.cannotPickup() && itemEntity.isAlive();
        FOLLOWABLE_DROP_FILTER = (entity) -> entity.isAlive();
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }
}
