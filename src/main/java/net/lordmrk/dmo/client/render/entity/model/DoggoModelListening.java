package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelListening extends DoggoEntityModel<DoggoEntity> {

    public DoggoModelListening(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
        float anim = (float) (-(1 / (1 + Math.pow(2.7182818F, -(MathHelper.sin(doggoEntity.getAnimationTick() / 8f) * 64f))) * 1.4f - 0.7f));

        if(doggoEntity.isInSittingPose()) {
            sitting(anim);
        } else {
            standing(anim, f, g);
        }
    }

    private void standing(float anim, float f, float g) {
        this.head.setPivot(-1.0F, 13.5F - anim / 2f, -7.0F);
        this.head.roll = anim;
        this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
        this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        this.leftFrontLeg.yaw = 0f;
        this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
        this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;
        this.leftHindLeg.yaw = 0f;
        this.neck.setPivot(-1.0F, 14.0F, -3.0F);
        this.neck.pitch = DEGREES_90;
        this.neck.yaw = 0f;
        this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
        this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + DEGREES_180) * 1.4F * g;
        this.rightFrontLeg.yaw = 0f;
        this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
        this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        this.rightHindLeg.yaw = 0f;
        this.tail.setPivot(-1.0F, 12.0F, 8.0F);
        this.torso.setPivot(0.0F, 14.0F, 2.0F);
        this.torso.pitch = DEGREES_90;
    }

    private void sitting(float anim) {
        this.head.setPivot(-1.0F, 14.0F - anim / 2f, -7.3F);
        this.head.roll = anim;
        this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
        this.leftFrontLeg.yaw = 0f;
        this.leftFrontLeg.pitch = DEGREES_333;
        this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
        this.leftHindLeg.yaw = 0f;
        this.leftHindLeg.pitch = DEGREES_270;
        this.neck.setPivot(-1.0F, 16.0F, -3.0F);
        this.neck.pitch = 1.2566371F;
        this.neck.yaw = 0.0F;
        this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
        this.rightHindLeg.yaw = 0f;
        this.rightHindLeg.pitch = DEGREES_270;
        this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
        this.rightFrontLeg.yaw = 0f;
        this.rightFrontLeg.pitch = DEGREES_333;
        this.tail.setPivot(-1.0F, 21.0F, 6.0F);
        this.torso.setPivot(0.0F, 18.0F, 0.0F);
        this.torso.pitch = DEGREES_45;
    }
}
