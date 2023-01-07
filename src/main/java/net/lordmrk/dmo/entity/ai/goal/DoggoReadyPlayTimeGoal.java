package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoFeeling;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;

public class DoggoReadyPlayTimeGoal extends Goal {

    private final DoggoEntity doggoEntity;
    private int mouthDelay = 0;

    public DoggoReadyPlayTimeGoal(DoggoEntity doggoEntity) {
        this.doggoEntity = doggoEntity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
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

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return false;
        }

        if(livingEntity.getAttacker() != null) {
            return false;
        }

        if(livingEntity.getStackInHand(Hand.MAIN_HAND) == null) {
            return false;
        }

        if(!livingEntity.getStackInHand(Hand.MAIN_HAND).isOf(DoggoModOverhauled.TENNIS_BALL)) {
            return false;
        }

        if(!this.doggoEntity.isEntityClose(livingEntity, 16.0D)) {
            return false;
        }

        return this.doggoEntity.getRandom().nextFloat() < 0.2F;
    }

    @Override
    public boolean canStop() {
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

        if(livingEntity.getStackInHand(Hand.MAIN_HAND) == null) {
            return true;
        }

        if(!livingEntity.getStackInHand(Hand.MAIN_HAND).isOf(DoggoModOverhauled.TENNIS_BALL)) {
            return true;
        }

        if(!this.doggoEntity.isEntityClose(livingEntity, 16.0D)) {
            return true;
        }

        List<ThrownItemEntity> list = this.doggoEntity.world.getEntitiesByClass(
                ThrownItemEntity.class,
                this.doggoEntity.getBoundingBox().expand(8.0D, 8.0D, 8.0D),
                DoggoEntity.FOLLOWABLE_DROP_FILTER);

        return list.size() > 0;
    }

    @Override
    public void start() {
        this.doggoEntity.setDamaged(false);
        this.doggoEntity.getNavigation().stop();
        this.doggoEntity.setAction(DoggoAction.PLAY_TIME);
        this.doggoEntity.setActionTicking(true);
        this.doggoEntity.setFeeling(DoggoFeeling.HAPPY);
        this.doggoEntity.setReadyToPlay(true);
    }

    @Override
    public void stop() {
        this.doggoEntity.setAction(DoggoAction.NEUTRAL);
        this.doggoEntity.setActionTicking(false);
        this.doggoEntity.setFeeling(DoggoFeeling.NEUTRAL);
    }

    @Override
    public void tick() {
        if(mouthDelay > 0) {
            mouthDelay--;
        } else if(mouthDelay == 0) {
            this.doggoEntity.setMouthOpened(true);
            mouthDelay = 60;
        }

        LivingEntity livingEntity = this.doggoEntity.getOwner();

        if(livingEntity == null) {
            return;
        }

        this.doggoEntity.getLookControl().lookAt(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
    }
}
