package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelReadyPlayTime extends DoggoEntityModel<DoggoEntity> {

    public DoggoModelReadyPlayTime(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
        playTime(doggoEntity);
    }

    private void playTime(DoggoEntity doggoEntity) {
        this.head.setPivot(-1.0F, 14.0F, -7.0F);
        this.head.roll = 0f;
        this.leftFrontLeg.setPivot(0.5F, 18.0F, -4.0F);
        this.leftFrontLeg.pitch = -0.87266462f;
        this.leftFrontLeg.yaw = -0.52359877f;
        this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
        this.leftHindLeg.pitch = 0f;
        this.leftHindLeg.yaw = 0f;
        this.neck.setPivot(-1.0F, 15.5F, -2.5F);
        this.neck.pitch = 1.5707964F;
        this.neck.yaw = 0f;
        this.rightFrontLeg.setPivot(-2.5F, 18.0F, -4.0F);
        this.rightFrontLeg.pitch = -0.87266462f;
        this.rightFrontLeg.yaw = 0.52359877f;
        this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
        this.rightHindLeg.pitch = 0f;
        this.rightHindLeg.yaw = 0f;
        this.tail.setPivot(-1.0F, 12.0F, 8.0F);
        this.torso.setPivot(0.0F, 14.0F, 2.0F);
        this.torso.pitch = 1.7453293f;
    }
}
