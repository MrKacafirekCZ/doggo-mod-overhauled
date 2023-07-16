package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelEatingFromBowl extends DoggoEntityModel<DoggoEntity> {
	
	public DoggoModelEatingFromBowl(ModelPart root) {
		super(root);
	}

	@Override
	public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
		this.head.setPivot(-1.0F, 13.5F, -7.0F);
		this.torso.pitch = DEGREES_90;
		this.neck.setPivot(-1.0F, 14.0F, -3.0F);
		this.neck.pitch = this.torso.pitch;
		this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
		this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;
		this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;
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
