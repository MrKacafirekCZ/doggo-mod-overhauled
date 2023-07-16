package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelSniffing extends DoggoEntityModel<DoggoEntity> {

    public DoggoModelSniffing(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
        this.head.setPivot(-1.0F, 16.5F, -5.5F);
        this.head.pitch = DEGREES_70;
        this.head.roll = 0f;
        this.torso.setPivot(0.0F, 14.0F, 2.0F);
        this.torso.pitch = DEGREES_90;
        this.neck.setPivot(-1.0F, 15.0F, -1.5F);
        this.neck.yaw = 0f;
        this.neck.pitch = DEGREES_110;
        this.tail.setPivot(-1.0F, 12.0F, 8.0F);
        this.realTail.pitch = 0f;

        this.leftFrontLeg.setPivot(0.5F, 16.0F, -3.0F);
        this.leftFrontLeg.yaw = 0f;
        this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        this.rightFrontLeg.setPivot(-2.5F, 16.0F, -3.0F);
        this.rightFrontLeg.yaw = 0f;
        this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;

        this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
        this.leftHindLeg.yaw = 0f;
        this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;
        this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
        this.rightHindLeg.yaw = 0f;
        this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
    }
}
