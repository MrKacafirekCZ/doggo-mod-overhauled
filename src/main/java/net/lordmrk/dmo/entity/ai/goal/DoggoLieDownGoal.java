package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.entity.DoggoEntity;
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
        if(!this.doggoEntity.shouldGoalStart()) {
            return false;
        }

        if(this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.001F;
    }

    @Override
    public boolean canStop() {
        if(this.actionTick <= 0) {
            return true;
        }

        if(this.doggoEntity.shouldGoalStop()) {
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
    }

    @Override
    public void tick() {
        this.actionTick--;
    }
}
