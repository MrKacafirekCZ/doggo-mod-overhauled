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
		this.head.yaw = 0.69813170f;
		this.head.pitch = 0f;
		this.head.roll = 0f;
		this.torso.setPivot(0.0F, 21.0F, 2.0F);
		this.torso.pitch = 1.5707964F;
		this.neck.setPivot(-1.4F, 21.5F, -2.2F);
		this.neck.yaw = 0.17453292f;
		this.neck.pitch = this.torso.pitch;
		this.neck.roll = 0f;
		this.tail.setPivot(-1.0F, 21.0F, 8.0F);
		this.tail.yaw = -0.5235987f;
		this.tail.pitch = 1.2217304f;
		this.rightFrontLeg.setPivot(-2.5F, 23.0F, -2.0F);
		this.rightFrontLeg.yaw = 1.04719755f;
		this.rightFrontLeg.pitch = -1.57079632f;
		this.leftFrontLeg.setPivot(0.5F, 23.0F, -4.0F);
		this.leftFrontLeg.yaw = 0.17453292f;
		this.leftFrontLeg.pitch = -1.57079632f;
		this.rightHindLeg.setPivot(-2.5F, 23.0F, 7.0F);
		this.rightHindLeg.yaw = 1.04719755f;
		this.rightHindLeg.pitch = -1.57079632f;
		this.leftHindLeg.setPivot(0.5F, 23.0F, 7.0F);
		this.leftHindLeg.yaw = 0.87266462f;
		this.leftHindLeg.pitch = -1.57079632f;
	}
}
