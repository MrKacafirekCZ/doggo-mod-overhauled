package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoFeeling;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class DoggoSniffingGoal extends Goal {

    private final DoggoEntity doggoEntity;
    private boolean canDig;
    private boolean canStop;
    private int minSniffs;
    private int minDigTime;
    private boolean navigateBack;
    private double startX;
    private double startY;
    private double startZ;

    public DoggoSniffingGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean shouldContinue() {
        return !canStop();
    }

    @Override
    public boolean canStart() {
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

        if(this.doggoEntity.isInSittingPose()) {
            return false;
        }

        if(this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        if(this.doggoEntity.hasAngerTime()) {
            return false;
        }

        if(this.doggoEntity.getActionDelay() > 0) {
            return false;
        }

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return false;
        }

        if(livingEntity.getAttacker() != null) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.01F;
    }

    @Override
    public boolean canStop() {
        if(this.canStop) {
            return true;
        }

        if(this.doggoEntity.hasBeenDamaged()) {
            return true;
        }

        if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
            return true;
        }

        if(!this.doggoEntity.isOnGround()) {
            return true;
        }

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return true;
        }

        if(livingEntity.getAttacker() != null) {
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        this.doggoEntity.setDamaged(false);
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setAction(DoggoAction.SNIFFING);
        this.doggoEntity.setActionTicking(true);
        this.doggoEntity.setFeeling(DoggoFeeling.HAPPY);

        this.startX = this.doggoEntity.getX();
        this.startY = this.doggoEntity.getY();
        this.startZ = this.doggoEntity.getZ();
        this.minSniffs = 5 + this.doggoEntity.getRandom().nextInt(3);
        this.minDigTime = 60 + this.doggoEntity.getRandom().nextInt(5) * 10;
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setActionTicking(false);
        this.doggoEntity.setFeeling(DoggoFeeling.NEUTRAL);
        this.doggoEntity.startActionDelay();
        this.canStop = false;
        this.canDig = false;
        this.navigateBack = false;
    }

    @Override
    public void tick() {
        if(this.doggoEntity.getNavigation().isIdle()) {
            if(this.canDig) {
                this.doggoEntity.setAction(DoggoAction.DIGGING);

                if(this.minDigTime == 0 && this.doggoEntity.getRandom().nextFloat() < 0.1f) {
                    if(this.doggoEntity.getRandom().nextInt(4) == 0) {
                        double x = MathHelper.sin(-this.doggoEntity.headYaw % 360 / 58) * 0.5f;
                        double z = MathHelper.cos(this.doggoEntity.headYaw % 360 / 58) * 0.5f;

                        ItemStack item;

                        if (this.doggoEntity.getRandom().nextInt(2) == 0) {
                            item = Items.IRON_NUGGET.getDefaultStack();
                        } else {
                            item = Items.GOLD_NUGGET.getDefaultStack();
                        }

                        ItemScatterer.spawn(this.doggoEntity.world, this.doggoEntity.getX() + x, this.doggoEntity.getY(), this.doggoEntity.getZ() + z, item);
                    }

                    this.canStop = true;
                    return;
                }

                if(this.minDigTime > 0) {
                    this.minDigTime--;
                }

                return;
            }

            if(this.minSniffs == 0 && this.doggoEntity.getRandom().nextFloat() < 0.1f) {
                this.canDig = true;
                this.canStop = this.doggoEntity.getRandom().nextFloat() < 0.5f;

                if(!this.doggoEntity.canStartDigging()) {
                    this.canStop = true;
                }
                return;
            }

            if(this.minSniffs > 0) {
                this.minSniffs--;
            }

            if(this.navigateBack) {
                this.doggoEntity.getNavigation().startMovingTo(startX, startY, startZ, 0.8);
            } else {
                float x = this.doggoEntity.getRandom().nextFloat() * 5 - this.doggoEntity.getRandom().nextFloat() * 5;
                float z = this.doggoEntity.getRandom().nextFloat() * 5 - this.doggoEntity.getRandom().nextFloat() * 5;
                this.doggoEntity.getNavigation().startMovingTo(startX + x, startY, startZ + z, 0.8);
            }

            this.navigateBack = !this.navigateBack;
        }
    }
}
