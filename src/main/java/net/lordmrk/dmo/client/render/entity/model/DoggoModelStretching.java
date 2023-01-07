package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelStretching extends DoggoEntityModel<DoggoEntity> {

	public DoggoModelStretching(ModelPart root) {
		super(root);
	}

	@Override
	public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
		float anim = (float) (-(1 / (1 + Math.pow(2.7182818F, -(MathHelper.sin((doggoEntity.getAnimationTick() - 20) / 40F) * 12))) * 7));

		this.head.setPivot(-1.0F, 13.5F - anim / 2.5F, -7.0F - anim / 3.8F);
		this.head.pitch = anim * 0.08726646f;
		this.head.roll = 0f;
		this.torso.setPivot(0.0F, 14.0F - anim / 2.2F, 2.0F);
		this.torso.pitch = 1.5707964F - anim * 0.08726646f;
		this.neck.setPivot(-1.0F, 14.0F - anim / 1.5F, -3.0F - anim / 3.8F);
		this.neck.yaw = 0f;
		this.neck.pitch = 1.5707964F - anim * 0.01745329f;
		this.leftFrontLeg.setPivot(0.5F, 16.0F - anim / 1.5F, -4.0F);
		this.leftFrontLeg.yaw = 0f;
		this.leftFrontLeg.pitch = anim * 0.17453292f;
		this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
		this.leftHindLeg.yaw = 0f;
		this.leftHindLeg.pitch = -anim * 0.00872665f;
		this.rightFrontLeg.setPivot(-2.5F, 16.0F - anim / 1.5F, -4.0F);
		this.rightFrontLeg.yaw = 0f;
		this.rightFrontLeg.pitch = anim * 0.17453292f;
		this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
		this.rightHindLeg.yaw = 0f;
		this.rightHindLeg.pitch = -anim * 0.00872665f;
		this.tail.setPivot(-1.0F, 12.0F, 8.0F + anim / 3F);
		this.realTail.pitch = -anim * 0.17453292f;
	}
}
