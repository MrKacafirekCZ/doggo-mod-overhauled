package net.lordmrk.dmo.client.render.entity;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.DoggoModOverhauledClient;
import net.lordmrk.dmo.client.render.entity.feature.DoggoCollarFeatureRenderer;
import net.lordmrk.dmo.client.render.entity.feature.DoggoHeldItemFeatureRenderer;
import net.lordmrk.dmo.client.render.entity.model.*;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class DoggoEntityRenderer extends MobEntityRenderer<DoggoEntity, DoggoEntityModel<DoggoEntity>> {

    private static final Identifier WILD_TEXTURE = new Identifier(DoggoModOverhauled.MODID, "textures/entity/doggo/doggo.png");
    private static final Identifier TAMED_TEXTURE = new Identifier(DoggoModOverhauled.MODID, "textures/entity/doggo/doggo_tame.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier(DoggoModOverhauled.MODID, "textures/entity/doggo/doggo_angry.png");
    private static final Identifier SLEEPING_TEXTURE = new Identifier(DoggoModOverhauled.MODID, "textures/entity/doggo/doggo_sleeping.png");

    private final Map<DoggoAction, DoggoEntityModel<DoggoEntity>> doggoModels = new HashMap();

    public DoggoEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DoggoEntityModel(context.getPart(DoggoModOverhauledClient.DOGGO)), 0.5F);
        this.addFeature(new DoggoCollarFeatureRenderer(this));
        this.addFeature(new DoggoHeldItemFeatureRenderer(this));

        this.doggoModels.put(DoggoAction.DIGGING, new DoggoModelDigging(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.EATING, new DoggoModelNeutral(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.EATING_FROM_BOWL, new DoggoModelEatingFromBowl(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.LISTENING, new DoggoModelListening(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.NAPPING, new DoggoModelNapping(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.NEUTRAL, new DoggoModelNeutral(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.PLAY_IN_SNOW, new DoggoModelPlayInSnow(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.PLAY_TIME, new DoggoModelReadyPlayTime(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.SCRATCHING, new DoggoModelScratching(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.SNIFFING, new DoggoModelSniffing(context.getPart(DoggoModOverhauledClient.DOGGO)));
        this.doggoModels.put(DoggoAction.STRETCHING, new DoggoModelStretching(context.getPart(DoggoModOverhauledClient.DOGGO)));
    }

    protected float getAnimationProgress(DoggoEntity doggoEntity, float f) {
        return doggoEntity.getTailAngle();
    }

    public void render(DoggoEntity doggoEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = this.doggoModels.get(doggoEntity.getAction());

        if (doggoEntity.isFurWet()) {
            float h = doggoEntity.getFurWetBrightnessMultiplier(g);
            this.model.setColorMultiplier(h, h, h);
        }

        super.render(doggoEntity, f, g, matrixStack, vertexConsumerProvider, i);
        if (doggoEntity.isFurWet()) {
            this.model.setColorMultiplier(1.0F, 1.0F, 1.0F);
        }

    }

    public Identifier getTexture(DoggoEntity doggoEntity) {
        if (doggoEntity.isTamed()) {
            if (doggoEntity.isAction(DoggoAction.NAPPING) ||
                    doggoEntity.isAction(DoggoAction.STRETCHING) && doggoEntity.getAnimationTick() > 60 && doggoEntity.getAnimationTick() < 130) {
                return SLEEPING_TEXTURE;
            }

            return TAMED_TEXTURE;
        } else {
            return doggoEntity.hasAngerTime() ? ANGRY_TEXTURE : WILD_TEXTURE;
        }
    }
}
