package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;

public class DoggoModelNapping extends DoggoEntityModel<DoggoEntity> {
	
	public DoggoModelNapping(ModelPart root) {
		super(root);
	}

	@Override
	public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
		if(doggoEntity.isBaby()) {
			this.head.setPivot(-2.5F, 16.0F, -6.0F);
		} else {
			this.head.setPivot(-2.5F, 21.0F, -6.0F);
		}
		this.head.yaw = DEGREES_40;
		this.head.pitch = 0f;
		this.head.roll = 0f;
		this.torso.setPivot(0.0F, 21.0F, 2.0F);
		this.torso.pitch = DEGREES_90;
		this.neck.setPivot(-1.4F, 21.5F, -2.2F);
		this.neck.yaw = DEGREES_10;
		this.neck.pitch = this.torso.pitch;
		this.neck.roll = 0f;
		this.tail.setPivot(-1.0F, 21.0F, 8.0F);
		this.tail.yaw = -DEGREES_30;
		this.tail.pitch = DEGREES_70;
		this.rightFrontLeg.setPivot(-2.5F, 23.0F, -2.0F);
		this.rightFrontLeg.yaw = DEGREES_60;
		this.rightFrontLeg.pitch = -DEGREES_90;
		this.leftFrontLeg.setPivot(0.5F, 23.0F, -4.0F);
		this.leftFrontLeg.yaw = DEGREES_10;
		this.leftFrontLeg.pitch = -DEGREES_90;
		this.rightHindLeg.setPivot(-2.5F, 23.0F, 7.0F);
		this.rightHindLeg.yaw = DEGREES_60;
		this.rightHindLeg.pitch = -DEGREES_90;
		this.leftHindLeg.setPivot(0.5F, 23.0F, 7.0F);
		this.leftHindLeg.yaw = DEGREES_50;
		this.leftHindLeg.pitch = -DEGREES_90;
	}
}
