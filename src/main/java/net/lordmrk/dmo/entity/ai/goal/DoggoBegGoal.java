package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DoggoBegGoal extends Goal {
	private final DoggoEntity doggoEntity;
	private PlayerEntity begFrom;
	private final World world;
	private final float begDistance;
	private int timer;
	private final TargetPredicate validPlayerPredicate;

	public DoggoBegGoal(DoggoEntity doggoEntity, float begDistance) {
	      this.doggoEntity = doggoEntity;
	      this.world = doggoEntity.world;
	      this.begDistance = begDistance;
	      this.validPlayerPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(begDistance);
	      this.setControls(EnumSet.of(Goal.Control.LOOK));
	}

	public boolean canStart() {
		if(this.doggoEntity.hasStackInMouth()) {
			return false;
		}
		
		this.begFrom = this.world.getClosestPlayer(this.validPlayerPredicate, this.doggoEntity);
		return this.begFrom != null && this.isAttractive(this.begFrom);
	}

	public boolean shouldContinue() {
		if(!this.begFrom.isAlive()) {
			return false;
		} else if(this.doggoEntity.squaredDistanceTo(this.begFrom) > (double) (this.begDistance * this.begDistance)) {
			return false;
		} else if(this.doggoEntity.hasStackInMouth()) {
			return false;
		} else if(this.doggoEntity.hasAngerTime()) {
			return false;
		} else {
			return this.timer > 0 && this.isAttractive(this.begFrom);
		}
	}

	public void start() {
		this.doggoEntity.setBegging(true);
		this.timer = 40 + this.doggoEntity.getRandom().nextInt(40);
	}

	public void stop() {
		this.doggoEntity.setBegging(false);
		this.begFrom = null;
	}

	public void tick() {
		this.doggoEntity.getLookControl().lookAt(this.begFrom.getX(), this.begFrom.getEyeY(), this.begFrom.getZ(), 10.0F, (float) this.doggoEntity.getMaxLookPitchChange());
		--this.timer;
	}

	private boolean isAttractive(PlayerEntity player) {
		for (Hand hand : Hand.values()) {
			ItemStack itemStack = player.getStackInHand(hand);

			if (this.doggoEntity.isTamed() && itemStack.getItem() == Items.BONE) {
				return true;
			}

			if (this.doggoEntity.isBreedingItem(itemStack)) {
				return true;
			}
		}

		return false;
	}
}
