package net.lordmrk.dmo.client.render.entity.model;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class DoggoModelPlayInSnow extends DoggoEntityModel<DoggoEntity> {

    public DoggoModelPlayInSnow(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(DoggoEntity doggoEntity, float f, float g, float h) {
        playTime(doggoEntity);
    }

    private void playTime(DoggoEntity doggoEntity) {
        int animation = doggoEntity.getAnimation() % 45;
        float anim;
        float animHead = 0;

        if (animation > PI_13) {
            animHead = 0;
        } else if (animation > PI_11) {
            animHead = MathHelper.sin(animation / 2f) * 1.25f - 1.25f;
        } else if (animation > PI_10) {
            animHead = MathHelper.sin(animation / 2f) * 2.5f;
        }

        if (animation > PI_49 / 4f) {
            anim = 0;
        } else if (animation > PI_43 / 4f) {
            anim = MathHelper.sin((animation * 2f + PI) / 3f) - 1f;
        } else if (animation > PI_10) {
            anim = MathHelper.sin((animation * 2f + PI) / 3f) * 2f;
        } else if (animation > PI_8) {
            anim = MathHelper.cos(animation / 4f) * 5f;
            animHead = anim;
        } else if (animation > PI_3) {
            anim = MathHelper.cos((animation + PI_2) / 5f) * 3f + 2f;
            animHead = anim;
        } else {
            anim = MathHelper.cos(animation / 3f) / 2f - 0.5f;
            animHead = anim;
        }

        float frontLeg = MathHelper.clamp(anim, 0, anim);

        this.head.setPivot(-1.0F, 13.5F - animHead, -7.0F + MathHelper.abs(anim) / 1.2f);
        this.head.pitch = DEGREES_60 - frontLeg * DEGREES_4;
        this.head.roll = 0f;
        this.leftFrontLeg.setPivot(0.5F, 16.0F - frontLeg, -4.0F + MathHelper.abs(frontLeg) / 1.1f);
        this.leftFrontLeg.pitch = -frontLeg * DEGREES_4;
        this.leftFrontLeg.yaw = 0f;
        this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
        this.leftHindLeg.pitch = 0f;
        this.leftHindLeg.yaw = 0f;
        this.neck.setPivot(-1.0F, 14.0F - anim, -3.0F + MathHelper.abs(anim) * 1.05f);
        this.neck.pitch = DEGREES_90 - anim * DEGREES_6;
        this.neck.yaw = 0f;
        this.rightFrontLeg.setPivot(-2.5F, 16.0F - frontLeg, -4.0F + MathHelper.abs(frontLeg) / 1.1f);
        this.rightFrontLeg.pitch = -frontLeg * DEGREES_4;
        this.rightFrontLeg.yaw = 0f;
        this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
        this.rightHindLeg.pitch = 0f;
        this.rightHindLeg.yaw = 0f;
        this.tail.setPivot(-1.0F, 12.0F + anim / 1.25f, 8.0F + MathHelper.abs(anim) / 1.5f);
        this.torso.setPivot(0.0F, 14.0F - anim / 2.5f, 2.0F + MathHelper.abs(anim) / 1.5f);
        this.torso.pitch = DEGREES_90 - anim * DEGREES_10;
    }
}
