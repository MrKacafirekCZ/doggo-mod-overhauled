package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DoggoLieDownGoal extends Goal {

    private int actionTick;
    private final DoggoEntity doggoEntity;

    public DoggoLieDownGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
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

        if(this.doggoEntity.hasBeenDamaged()) {
            return false;
        }

        if(this.doggoEntity.hasAngerTime()) {
            return false;
        }

        if(this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        if(this.doggoEntity.getActionDelay() > 0) {
            return false;
        }

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return true;
        }

        if(this.doggoEntity.squaredDistanceTo(livingEntity) > 144.0D) {
            return false;
        }

        if(livingEntity.getAttacker() != null) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.01F;
    }

    @Override
    public boolean canStop() {
        if(this.actionTick <= 0) {
            return true;
        }

        if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
            return true;
        }

        if(!this.doggoEntity.isOnGround()) {
            return true;
        }

        if(this.doggoEntity.hasBeenDamaged()) {
            return true;
        }

        if(this.doggoEntity.hasAngerTime()) {
            return true;
        }

        if(!this.doggoEntity.isOwnerClose()) {
           return true;
        }

        return false;
    }

    @Override
    public void start() {
        this.actionTick = this.doggoEntity.getRandom().nextInt(200) + 100;
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setInLieDownPose(true);
    }

    @Override
    public void stop() {
        this.doggoEntity.setInLieDownPose(false);
        this.doggoEntity.startActionDelay();
    }

    @Override
    public void tick() {
        this.actionTick--;
    }
}
