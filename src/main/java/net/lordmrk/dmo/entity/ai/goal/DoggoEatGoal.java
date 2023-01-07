package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoFeeling;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.FoodComponent;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class DoggoEatGoal extends Goal {

	private final DoggoEntity doggoEntity;
	private int eating;
	
	public DoggoEatGoal(DoggoEntity doggoEntity) {
		this.doggoEntity = doggoEntity;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return eating > 0;
	}

	@Override
	public boolean canStart() {
		if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoEntity.isOnGround()) {
			return false;
		} else if(this.doggoEntity.hasAngerTime()) {
			return false;
		}
		
		return doggoEntity.isStackInMouthMeat() && (doggoEntity.getHealth() < doggoEntity.getMaxHealth() || doggoEntity.getRandom().nextFloat() < 0.01F);
	}
	
	@Override
	public boolean canStop() {
		return this.doggoEntity.hasBeenDamaged() || eating == 0;
	}

	@Override
	public void start() {
		this.doggoEntity.setDamaged(false);
		eating = doggoEntity.getStackInMouth().getItem().getFoodComponent().getHunger() * 4;
		
		if(doggoEntity.isSitting()) {
			doggoEntity.setInSittingPose(true);
		}

		doggoEntity.setAction(DoggoAction.EATING);
		doggoEntity.setActionTicking(true);
		doggoEntity.setFeeling(DoggoFeeling.HAPPY);
	}
	
	@Override
	public void stop() {
		doggoEntity.setStackInMouth(null);
		
		if(!doggoEntity.isSitting()) {
			doggoEntity.setInSittingPose(false);
		}

		doggoEntity.setAction(DoggoAction.NEUTRAL);
		doggoEntity.setActionTicking(false);
		doggoEntity.setFeeling(DoggoFeeling.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(eating % 2 == 0) {
			if(eating % 4 == 0) {
				if((int) doggoEntity.getHealth() + 1 <= doggoEntity.getMaxHealth()) {
					doggoEntity.heal(1);
				}
			}

			doggoEntity.world.playSound(null,
					doggoEntity.getX(), doggoEntity.getY(), doggoEntity.getZ(),
					SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 0.4f, 1f);
		}
		
		eating--;
	}
}
