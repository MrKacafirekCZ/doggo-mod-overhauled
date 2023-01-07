package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelNeutral extends DoggoEntityModel<DoggoEntity> {
	
	public DoggoModelNeutral(ModelPart root) {
		super(root);
	}

	@Override
	public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
		if(doggoEntity.isInSittingPose()) {
			sitting();
		} else {
			standing(f, g);
		}
		
		shakingAndBegging(doggoEntity, h);
	}
	
	private void sitting() {
		this.head.setPivot(-1.0F, 14.0F, -7.3F);
		this.head.roll = 0f;
		this.neck.setPivot(-1.0F, 16.0F, -3.0F);
		this.neck.pitch = 1.2566371F;
		this.neck.yaw = 0.0F;
		this.torso.setPivot(0.0F, 18.0F, 0.0F);
		this.torso.pitch = 0.7853982F;
		this.tail.setPivot(-1.0F, 21.0F, 6.0F);
		this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
		this.rightHindLeg.yaw = 0f;
		this.rightHindLeg.pitch = 4.712389F;
		this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
		this.leftHindLeg.yaw = 0f;
		this.leftHindLeg.pitch = 4.712389F;
		this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
		this.rightFrontLeg.yaw = 0f;
		this.rightFrontLeg.pitch = 5.811947F;
		this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
		this.leftFrontLeg.yaw = 0f;
		this.leftFrontLeg.pitch = 5.811947F;
	}
	
	private void shakingAndBegging(DoggoEntity doggoEntity, float h) {
		this.realHead.roll = doggoEntity.getBegAnimationProgress(h) + doggoEntity.getShakeAnimationProgress(h, 0.0F);
		this.neck.roll = doggoEntity.getShakeAnimationProgress(h, -0.08F);
		this.torso.roll = doggoEntity.getShakeAnimationProgress(h, -0.16F);
		this.realTail.roll = doggoEntity.getShakeAnimationProgress(h, -0.2F);
	}
	
	private void standing(float f, float g) {
		this.head.setPivot(-1.0F, 13.5F, -7.0F);
		this.torso.pitch = 1.5707964F;
		this.neck.setPivot(-1.0F, 14.0F, -3.0F);
		this.neck.pitch = this.torso.pitch;
		this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
		this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
		this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
		this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.neck.yaw = 0f;
		this.head.roll = 0f;
		this.torso.setPivot(0.0F, 14.0F, 2.0F);
		this.tail.setPivot(-1.0F, 12.0F, 8.0F);
		this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
		this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
		this.rightHindLeg.yaw = 0f;
		this.leftHindLeg.yaw = 0f;
		this.rightFrontLeg.yaw = 0f;
		this.leftFrontLeg.yaw = 0f;
	}
}
