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
		this.head.yaw = 0.1745329f;
		this.head.pitch = -0.17453292f * (4f + MathHelper.sin(doggoEntity.getAnimationTick() * 2f) * 0.1f);
		this.head.roll = 0.17453292f * (5f + MathHelper.sin(doggoEntity.getAnimationTick() * 2f) * 0.1f);
		this.neck.setPivot(-0.6F, 16.5F, -1.8F);
		this.neck.pitch = 0.8726646f;
		this.neck.yaw = -0.17453292f;
		this.neck.roll = 0.3490658f;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = 0.7853982F;
		this.tail.setPivot(-0.2F, 21.0F, 4.5F);
		this.tail.yaw = 0.8726646f;
		this.tail.pitch = 1.3089969f;
		this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
		this.rightHindLeg.yaw = -0.17453292f;
		this.rightHindLeg.pitch = 4.712389F;
		this.leftHindLeg.setPivot(1.1F, 22F, 1.6F);
		this.leftHindLeg.yaw = -0.8726646f;
		this.leftHindLeg.pitch = MathHelper.cos(doggoEntity.getAnimationTick() * 2.5f) * 0.15f - 2.26892802f;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.2F);
		this.rightFrontLeg.yaw = 0.17453292f;
		this.rightFrontLeg.pitch = 5.811947F;
		this.leftFrontLeg.setPivot(0.51F, 17.0F, -3.5F);
		this.leftFrontLeg.yaw = -0.5235987f;
		this.leftFrontLeg.pitch = 5.811947F;
	}
	
	private void scratchingRight(DoggoEntity doggoWolf) {
		this.head.setPivot(-5.0F, 15F, -4.5F);
		this.head.yaw = -0.3490658f;
		this.head.pitch = -0.17453292f * (4f + MathHelper.sin(doggoWolf.getAnimationTick() * 2f) * 0.1f);
		this.head.roll = -0.17453292f * (5f + MathHelper.sin(doggoWolf.getAnimationTick() * 2f) * 0.1f);
		this.neck.setPivot(-1.4F, 17.0F, -1.4F);
		this.neck.pitch = 0.8726646f;
		this.neck.yaw = 0.17453292f;
		this.neck.roll = -0.3490658f;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = 0.7853982F;
		this.tail.setPivot(-0.2F, 21.0F, 4.5F);
		this.tail.yaw = -0.8726646f;
		this.tail.pitch = 1.3089969f;
		this.rightHindLeg.setPivot(-2.5F, 22.4F, 1.8F);
		this.rightHindLeg.yaw = 0.8726646f;
		this.rightHindLeg.pitch = MathHelper.cos(doggoWolf.getAnimationTick() * 2.5f) * 0.15f - 2.26892802f;
		this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
		this.leftHindLeg.yaw = 0.3490658f;
		this.leftHindLeg.pitch = 4.712389F;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -3.0F);
		this.rightFrontLeg.yaw = 0.5235987f;
		this.rightFrontLeg.pitch = 5.811947F;
		this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.5F);
		this.leftFrontLeg.yaw = -0.17453292f;
		this.leftFrontLeg.pitch = 5.811947F;
	}
}
