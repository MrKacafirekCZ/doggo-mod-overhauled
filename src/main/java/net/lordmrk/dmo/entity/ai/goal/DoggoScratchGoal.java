package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoScratchGoal extends Goal {

	private final DoggoEntity doggoEntity;
	private int scratchTick;

	public DoggoScratchGoal(DoggoEntity doggoEntity) {
		this.doggoEntity = doggoEntity;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return this.scratchTick > 0 && !this.doggoEntity.isInsideWaterOrBubbleColumn() && this.doggoEntity.isOnGround();
	}

	@Override
	public boolean canStart() {
		if(!this.doggoEntity.isTamed()) {
			return false;
		} else if(this.doggoEntity.isBaby()) {
			return false;
		} else if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoEntity.isOnGround()) {
			return false;
		} else if(this.doggoEntity.hasStackInMouth()) {
			return false;
		} else if(this.doggoEntity.getActionDelay() > 0) {
			return false;
		} else if(this.doggoEntity.hasAngerTime()) {
			return false;
		} else {
			LivingEntity livingEntity = this.doggoEntity.getOwner();
			if(livingEntity == null) {
				return true;
			} else {
				return this.doggoEntity.squaredDistanceTo(livingEntity) < 144.0D && livingEntity.getAttacker() != null ? false : this.doggoEntity.getRandom().nextFloat() < 0.01F;
			}
		}
	}
	
	@Override
	public boolean canStop() {
		return this.doggoEntity.hasBeenDamaged() || this.scratchTick <= 0;
	}

	@Override
	public void start() {
		this.doggoEntity.setDamaged(false);
		this.doggoEntity.getNavigation().stop();
		this.doggoEntity.setAction(DoggoAction.SCRATCHING);
		this.doggoEntity.setActionTicking(true);
		this.doggoEntity.setScratchingSide(this.doggoEntity.getRandom().nextInt(2));
		this.scratchTick = 60 + this.doggoEntity.getRandom().nextInt(80);
	}

	@Override
	public void stop() {
		this.doggoEntity.setAction(DoggoAction.NEUTRAL);
		this.doggoEntity.setActionTicking(false);
		this.doggoEntity.startActionDelay();
	}
	
	@Override
	public void tick() {
		this.scratchTick--;
	}
}
