package net.lordmrk.dmo.entity.ai.goal;

import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoFeeling;
import net.lordmrk.dmo.block.DogBowl;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DoggoEatFromBowlGoal extends Goal {

	private final DoggoEntity doggoEntity;
	private boolean failed;
	private Optional<BlockPos> bowlPos;
	private int eating;
	private int waitIfFailed;
	private int whineTime;

	public DoggoEatFromBowlGoal(DoggoEntity doggoEntity) {
		this.doggoEntity = doggoEntity;
		this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean shouldContinue() {
		return !failed && waitIfFailed == 0 && this.doggoEntity.getHealth() < this.doggoEntity.getMaxHealth() && !this.doggoEntity.isNavigating() && isNextToBowl() || eating > 0 || whineTime > 0;
	}

	@Override
	public boolean canStart() {
		if(!this.doggoEntity.isTamed()) {
			return false;
		} else if(this.doggoEntity.isInsideWaterOrBubbleColumn()) {
			return false;
		} else if(!this.doggoEntity.isOnGround()) {
			return false;
		} else if(this.doggoEntity.hasStackInMouth()) {
			return false;
		} else if(this.doggoEntity.hasAngerTime()) {
			return false;
		} else if(this.doggoEntity.getHealth() == this.doggoEntity.getMaxHealth()){
			return false;
		} else if(this.waitIfFailed > 0){
			return this.doggoEntity.getRandom().nextFloat() < 0.02F;
		}
		
		return true;
	}
	
	@Override
	public boolean canStop() {
		return this.doggoEntity.hasBeenDamaged() || this.doggoEntity.getHealth() == this.doggoEntity.getMaxHealth();
	}

	@Override
	public void start() {
		this.doggoEntity.setDamaged(false);
		
		if(waitIfFailed > 0) {
			waitIfFailed--;
			return;
		}
		
		this.failed = false;
		this.eating = 0;
		this.whineTime = 0;
		this.bowlPos = BlockPos.findClosest(
				this.doggoEntity.getBlockPos(),
				16, 8,
				(b) -> DogBowl.canEatFromBowl(b, doggoEntity));
		
		try {
			Vec3d closestSide = getClosestSide(new Vec3d(bowlPos.get().getX(), bowlPos.get().getY(), bowlPos.get().getZ()));
			
			this.doggoEntity.getNavigation().startMovingTo(closestSide.getX(), closestSide.getY(), closestSide.getZ(), 1);
		} catch(NoSuchElementException ex) {
			fail();
		}
	}

	@Override
	public void stop() {
		this.doggoEntity.setAction(DoggoAction.NEUTRAL);
		this.doggoEntity.setActionTicking(false);
		this.doggoEntity.setFeeling(DoggoFeeling.NEUTRAL);
	}
	
	@Override
	public void tick() {
		if(!failed) {
			if(this.doggoEntity.isAction(DoggoAction.EATING_FROM_BOWL)) {
				this.doggoEntity.getLookControl().lookAt(
						this.doggoEntity.getBowlPos().getX() + 0.5,
						0,
						this.doggoEntity.getBowlPos().getZ() + 0.5, 10, 10);
				
				if(eating == 0) {
					DogBowlEntity dogBowlEntity = (DogBowlEntity) this.doggoEntity.getWorld().getBlockEntity(this.doggoEntity.getBowlPos());

					if(dogBowlEntity != null && dogBowlEntity.hasFood()) {
						eating = dogBowlEntity.getFoodHunger();
						dogBowlEntity.foodEaten();
					} else if((int) this.doggoEntity.getHealth() + 1 <= this.doggoEntity.getMaxHealth()) {
						failAndWhine();
						return;
					}
				}
				
				if(eating % 2 == 0) {
					if(eating % 4 == 0) {
						if((int) this.doggoEntity.getHealth() + 1 <= this.doggoEntity.getMaxHealth()) {
							this.doggoEntity.heal(1);
						}
					}

					this.doggoEntity.getWorld().playSound(null,
							this.doggoEntity.getX(), this.doggoEntity.getY(), this.doggoEntity.getZ(),
							SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 0.4f, 1f);
				}
				
				if(eating > 0) {
					eating--;
				}
			} else if(this.bowlPos.isPresent() && isNextToBowl()) {
				if(this.doggoEntity.isNavigating()) {
					this.doggoEntity.getNavigation().stop();
				}

				this.doggoEntity.setBowlPos(bowlPos.get());
				this.doggoEntity.setAction(DoggoAction.EATING_FROM_BOWL);
				this.doggoEntity.setActionTicking(true);
				this.doggoEntity.setFeeling(DoggoFeeling.HAPPY);
				this.doggoEntity.dropStackInMouth();
			} else if(!this.doggoEntity.isNavigating()) {
				fail();
			}
		} else if(whineTime > 0) {
			whineTime--;
		}
	}
	
	private void fail() {
		this.failed = true;
		this.waitIfFailed = 3;
	}
	
	private void failAndWhine() {
		fail();
		this.doggoEntity.getWorld().playSound(
				null,
				this.doggoEntity.getX(), this.doggoEntity.getY(), this.doggoEntity.getZ(),
				SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.NEUTRAL, 0.4f, 1.05f);
		whineTime = 40;
	}
	
	private Vec3d getClosestSide(Vec3d position) {
		Vec3d[] sides = new Vec3d[] {new Vec3d(0.4, 0, 0), new Vec3d(-0.4, 0, 0), new Vec3d(0, 0, 0.4), new Vec3d(0, 0, -0.4)};
		
		Vec3d closestSide = position.add(sides[0]).add(0.5, 0, 0.5);
		double closest = position.add(sides[0]).add(0.5, 0, 0.5).distanceTo(this.doggoEntity.getPos());
		
		for(int i = 1; i < sides.length; i++) {
			double close = position.add(sides[i]).add(0.5, 0, 0.5).distanceTo(this.doggoEntity.getPos());
			
			if(close < closest) {
				closest = close;
				closestSide = position.add(sides[i]).add(0.5, 0, 0.5);
			}
		}
		
		return closestSide;
	}
	
	private boolean isNextToBowl() {
		try {
			if(this.doggoEntity.getBlockPos().isWithinDistance(this.bowlPos.get(), 1.05)) {
				return true;
			}
			
			return false;
		} catch(NoSuchElementException ex) {
			return false;
		}
	}
}
