package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelScratching extends DoggoEntityModel<DoggoEntity> {
	
	public DoggoModelScratching(ModelPart root) {
		super(root);
	}

	@Override
	public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
		if(doggoEntity.getScratchingSide() == 0) {
			scratchingLeft(doggoEntity);
		} else {
			scratchingRight(doggoEntity);
		}
	}
	
	private void scratchingLeft(DoggoEntity doggoEntity) {
		this.head.setPivot(4.0F, 14F, -3.8F);
		this.head.yaw = DEGREES_10;
		this.head.pitch = -DEGREES_10 * (4f + MathHelper.sin(doggoEntity.getAnimationTick() * 2f) * 0.1f);
		this.head.roll = DEGREES_10 * (5f + MathHelper.sin(doggoEntity.getAnimationTick() * 2f) * 0.1f);
		this.neck.setPivot(-0.6F, 16.5F, -1.8F);
		this.neck.pitch = DEGREES_50;
		this.neck.yaw = -DEGREES_10;
		this.neck.roll = DEGREES_20;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = DEGREES_45;
		this.tail.setPivot(-0.2F, 21.0F, 4.5F);
		this.tail.yaw = DEGREES_50;
		this.tail.pitch = DEGREES_75;

		this.leftFrontLeg.setPivot(0.51F, 17.0F, -3.5F);
		this.leftFrontLeg.yaw = -DEGREES_30;
		this.leftFrontLeg.pitch = DEGREES_333;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.2F);
		this.rightFrontLeg.yaw = DEGREES_10;
		this.rightFrontLeg.pitch = DEGREES_333;

		this.leftHindLeg.setPivot(1.1F, 22F, 1.6F);
		this.leftHindLeg.yaw = -DEGREES_50;
		this.leftHindLeg.pitch = MathHelper.cos(doggoEntity.getAnimationTick() * 2.5f) * 0.15f - DEGREES_130;
		this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
		this.rightHindLeg.yaw = -DEGREES_10;
		this.rightHindLeg.pitch = DEGREES_270;
	}
	
	private void scratchingRight(DoggoEntity doggoWolf) {
		this.head.setPivot(-5.0F, 15F, -4.5F);
		this.head.yaw = -DEGREES_20;
		this.head.pitch = -DEGREES_10 * (4f + MathHelper.sin(doggoWolf.getAnimationTick() * 2f) * 0.1f);
		this.head.roll = -DEGREES_10 * (5f + MathHelper.sin(doggoWolf.getAnimationTick() * 2f) * 0.1f);
		this.neck.setPivot(-1.4F, 17.0F, -1.4F);
		this.neck.pitch = DEGREES_50;
		this.neck.yaw = DEGREES_10;
		this.neck.roll = -DEGREES_20;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = DEGREES_45;
		this.tail.setPivot(-0.2F, 21.0F, 4.5F);
		this.tail.yaw = -DEGREES_50;
		this.tail.pitch = DEGREES_75;

		this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.5F);
		this.leftFrontLeg.yaw = -DEGREES_10;
		this.leftFrontLeg.pitch = DEGREES_333;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -3.0F);
		this.rightFrontLeg.yaw = DEGREES_30;
		this.rightFrontLeg.pitch = DEGREES_333;

		this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
		this.leftHindLeg.yaw = DEGREES_20;
		this.leftHindLeg.pitch = DEGREES_270;
		this.rightHindLeg.setPivot(-2.5F, 22.4F, 1.8F);
		this.rightHindLeg.yaw = DEGREES_50;
		this.rightHindLeg.pitch = MathHelper.cos(doggoWolf.getAnimationTick() * 2.5f) * 0.15f - DEGREES_130;
	}
}
