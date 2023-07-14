package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;
import java.util.List;

public class DoggoPlayTennisBallGoal extends Goal {

    private final DoggoEntity doggoEntity;
    private ThrownItemEntity thrownItemEntity;
    private ItemEntity itemEntity;
    private boolean failed;

    public DoggoPlayTennisBallGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean shouldContinue() {
        return !canStop();
    }

    @Override
    public boolean canStart() {
        if(failed) {
            return false;
        }

        if(!this.doggoEntity.isTamed()) {
            return false;
        }

        if(this.doggoEntity.isBaby()) {
            return false;
        }

        if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
            return false;
        }

        if(!this.doggoEntity.isOnGround()) {
            return false;
        }

        if(this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        if(this.doggoEntity.hasAngerTime()) {
            return false;
        }

        if(this.doggoEntity.getOwner() == null) {
            return false;
        }

        if(!this.doggoEntity.isReadyToPlay()) {
            return false;
        }

        List<ThrownItemEntity> list = this.doggoEntity.getWorld().getEntitiesByClass(
                ThrownItemEntity.class,
                this.doggoEntity.getBoundingBox().expand(8.0D, 8.0D, 8.0D),
                DoggoEntity.FOLLOWABLE_DROP_FILTER);

        if(list.size() == 0) {
            return false;
        }

        this.thrownItemEntity = list.get(0);
        return this.thrownItemEntity != null;
    }

    @Override
    public boolean canStop() {
        if(failed) {
            return true;
        }

        if(this.doggoEntity.hasBeenDamaged()) {
            return true;
        }

        if(this.thrownItemEntity != null || this.itemEntity != null || this.doggoEntity.hasStackInMouth(DoggoModOverhauled.TENNIS_BALL)) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        this.doggoEntity.setDamaged(false);
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.getNavigation().startMovingTo(thrownItemEntity, 1.5);
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setReadyToPlay(false);
        this.thrownItemEntity = null;
        this.itemEntity = null;
        this.failed = false;
    }

    @Override
    public void tick() {
        if(this.doggoEntity.isTouchingWater() && this.doggoEntity.getFluidHeight(FluidTags.WATER) > this.doggoEntity.getSwimHeight() || this.doggoEntity.isInLava()) {
            if (this.doggoEntity.getRandom().nextFloat() < 0.8F) {
                this.doggoEntity.getJumpControl().setActive();
            }
        }

        if(this.thrownItemEntity != null) {
            if(this.thrownItemEntity.isRemoved()) {
                List<ItemEntity> list = this.doggoEntity.getWorld().getEntitiesByClass(
                        ItemEntity.class,
                        this.doggoEntity.getBoundingBox().expand(20.0D, 8.0D, 20.0D),
                        DoggoEntity.PICKABLE_DROP_FILTER);

                for(ItemEntity itemEntity : list) {
                    if(itemEntity.getStack().getItem() == DoggoModOverhauled.TENNIS_BALL) {
                        this.itemEntity = itemEntity;
                        break;
                    }
                }

                if(this.itemEntity == null) {
                    fail();
                }

                this.thrownItemEntity = null;
                return;
            }

            if(this.doggoEntity.getNavigation().isIdle()) {
                this.doggoEntity.getNavigation().startMovingTo(thrownItemEntity, 1.3);
            }
            return;
        }

        if(this.itemEntity != null) {
            if(this.itemEntity.isRemoved()) {
                if(!this.doggoEntity.hasStackInMouth(DoggoModOverhauled.TENNIS_BALL)) {
                    fail();
                }

                this.itemEntity = null;
                return;
            }

            if(this.doggoEntity.squaredDistanceTo(itemEntity) < 3) {
                this.doggoEntity.getNavigation().stop();

                if(this.doggoEntity.hasStackInMouth()) {
                    this.doggoEntity.dropStackInMouth();
                }

                this.doggoEntity.setStackInMouth(this.itemEntity.getStack());
                this.itemEntity.remove(Entity.RemovalReason.KILLED);
                this.doggoEntity.getNavigation().startMovingTo(this.doggoEntity.getOwner(), 1);
                return;
            }

            if(this.doggoEntity.getNavigation().isIdle()) {
                this.doggoEntity.getNavigation().startMovingTo(itemEntity, 1.5);
            }
            return;
        }

        if(this.doggoEntity.hasStackInMouth(DoggoModOverhauled.TENNIS_BALL)) {
            if(this.doggoEntity.getNavigation().isIdle()) {
                this.doggoEntity.getNavigation().startMovingTo(this.doggoEntity.getOwner(), 1);
            }

            if(this.doggoEntity.squaredDistanceTo(this.doggoEntity.getOwner()) < 3) {
                this.doggoEntity.getNavigation().stop();
                this.doggoEntity.dropStackInMouth();

                this.itemEntity = null;
                this.thrownItemEntity = null;
            }

            return;
        }
    }

    private void fail() {
        this.failed = true;
        this.doggoEntity.getWorld().playSound(
                null,
                this.doggoEntity.getX(), this.doggoEntity.getY(), this.doggoEntity.getZ(),
                SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.NEUTRAL, 0.4f, 1.05f);
    }
}
