package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoNapGoal extends Goal {

	private final DoggoEntity doggoEntity;

	public DoggoNapGoal(DoggoEntity doggoEntity) {
		this.doggoEntity = doggoEntity;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return !this.doggoEntity.isInsideWaterOrBubbleColumn() && this.doggoEntity.isOnGround() && !this.doggoEntity.isOwnerClose();
	}

	@Override
	public boolean canStart() {
		if(!this.doggoEntity.isTamed()) {
			return false;
		} else if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoEntity.isOnGround()) {
			return false;
		} else if(!this.doggoEntity.isInSittingPose()) {
			return false;
		} else if(this.doggoEntity.hasStackInMouth()) {
			return false;
		} else if(this.doggoEntity.isOwnerClose()) {
			return false;
		} else if(this.doggoEntity.hasAngerTime()) {
			return false;
		} else {
			LivingEntity livingEntity = this.doggoEntity.getOwner();
			if(livingEntity == null) {
				return true;
			} else {
				return livingEntity.getAttacker() != null ? false : this.doggoEntity.getRandom().nextFloat() < 0.01F;
			}
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoEntity.hasBeenHurt() || this.doggoEntity.isOwnerClose();
	}

	@Override
	public void start() {
		this.doggoEntity.getNavigation().stop();
		this.doggoEntity.setAction(DoggoAction.NAPPING);
	}

	@Override
	public void stop() {
		this.doggoEntity.setAction(DoggoAction.NEUTRAL);
	}
	
	@Override
	public void tick() {
		
	}
}
