package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DoggoListeningGoal extends Goal {

    private final DoggoEntity doggoEntity;
    private int actionTick;

    public DoggoListeningGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
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
            return false;
        }

        if(this.doggoEntity.squaredDistanceTo(livingEntity) > 144.0D) {
            return false;
        }

        if(livingEntity.getAttacker() != null) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.005F;
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

        return false;
    }

    @Override
    public void start() {
        this.doggoEntity.setDamaged(false);
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setAction(DoggoAction.LISTENING);
        this.doggoEntity.setActionTicking(true);

        this.actionTick = 64 + (this.doggoEntity.getRandom().nextInt(6) * 32);
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setActionTicking(false);
        this.doggoEntity.startActionDelay();

        this.doggoEntity.setInSittingPose(false);
    }

    @Override
    public void tick() {
        if(this.doggoEntity.isSitting() && !this.doggoEntity.isInSittingPose()) {
            this.doggoEntity.setInSittingPose(true);
        } else if(!this.doggoEntity.isSitting() && this.doggoEntity.isInSittingPose()) {
            this.doggoEntity.setInSittingPose(false);
        }

        this.actionTick--;
    }
}
