package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoGoalData;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DoggoPlayInSnowGoal extends Goal {

    private int actionTick;
    private final DoggoEntity doggoEntity;
    private final DoggoGoalData goalData;

    private static final DoggoAction ACTION = DoggoAction.PLAY_IN_SNOW;

    public DoggoPlayInSnowGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.goalData = doggoEntity.getGoalData(ACTION);
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!this.goalData.canStart()) {
            return false;
        }

        if (!this.doggoEntity.shouldGoalStart()) {
            return false;
        }

        if (this.doggoEntity.hasStackInMouth()) {
            return false;
        }

        if (!isInSnow()) {
            return false;
        }

        return goalData.shouldStart();
    }

    @Override
    public boolean canStop() {
        if (this.actionTick <= 0) {
            return true;
        }

        if (this.doggoEntity.shouldGoalStop()) {
            return true;
        }

        return false;
    }

    private boolean isInSnow() {
        if (this.doggoEntity.getWorld().getBlockState(this.doggoEntity.getBlockPos()).getBlock() == Blocks.SNOW) {
            return true;
        }

        if (this.doggoEntity.getWorld().getBlockState(this.doggoEntity.getBlockPos().down()).getBlock() == Blocks.SNOW_BLOCK) {
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return !canStop();
    }

    @Override
    public void start() {
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setAction(ACTION);
        this.doggoEntity.setActionTicking(true);

        this.actionTick = this.doggoEntity.getRandom().nextBoolean() ? 45 : 68;

        this.goalData.start();
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setActionTicking(false);

        this.goalData.stop();
    }

    @Override
    public void tick() {
        this.actionTick--;
    }
}
