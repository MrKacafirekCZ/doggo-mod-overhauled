package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DoggoStretchGoal extends Goal {

    private final DoggoEntity doggoEntity;
    private int actionTick;

    public DoggoStretchGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean shouldContinue() {
        return this.actionTick > 0 && !this.doggoEntity.isInsideWaterOrBubbleColumn() && this.doggoEntity.isOnGround();
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

        if(this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        if(this.doggoEntity.hasAngerTime()) {
            return false;
        }

        if(this.doggoEntity.getActionDelay() > 0) {
            return false;
        }

        if(this.doggoEntity.hasJustStretched()) {
            this.doggoEntity.setJustStretched(false);
            return false;
        }

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return true;
        }

        if(livingEntity.getAttacker() != null) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.01F;
    }

    @Override
    public boolean canStop() {
        return this.doggoEntity.hasBeenDamaged() || this.actionTick <= 0;
    }

    @Override
    public void start() {
        this.doggoEntity.setDamaged(false);
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setAction(DoggoAction.STRETCHING);
        this.doggoEntity.setActionTicking(true);

        this.actionTick = 180;
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setActionTicking(false);
        this.doggoEntity.setJustStretched(true);
        this.doggoEntity.startActionDelay();
    }

    @Override
    public void tick() {
        this.actionTick--;
    }
}
