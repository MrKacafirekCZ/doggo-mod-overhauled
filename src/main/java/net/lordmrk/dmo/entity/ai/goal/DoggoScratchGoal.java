package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoGoalData;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DoggoScratchGoal extends Goal {

	private int actionTick;
	private final DoggoEntity doggoEntity;
	private final DoggoGoalData goalData;

	private static final DoggoAction ACTION = DoggoAction.SCRATCHING;

	public DoggoScratchGoal(DoggoEntity doggoEntity) {
		this.doggoEntity = doggoEntity;
		this.goalData = doggoEntity.getGoalData(ACTION);
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return !canStop();
	}

	@Override
	public boolean canStart() {
		if (!this.goalData.canStart()) {
			return false;
		}

		if(!this.doggoEntity.shouldGoalStart()) {
			return false;
		}

		if(this.doggoEntity.hasStackInMouth()) {
			return false;
		}

		return this.goalData.shouldStart();
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
		this.doggoEntity.getNavigation().stop();
		this.doggoEntity.setAction(ACTION);
		this.doggoEntity.setActionTicking(true);
		this.doggoEntity.setScratchingSide(this.doggoEntity.getRandom().nextInt(2));
		this.actionTick = 40 + this.doggoEntity.getRandom().nextInt(100);

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
