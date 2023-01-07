package net.lordmrk.dmo.client.render.entity.feature;

import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.client.render.entity.model.DoggoEntityModel;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DoggoCollarFeatureRenderer extends FeatureRenderer<DoggoEntity, DoggoEntityModel<DoggoEntity>> {

    private static final Identifier SKIN = new Identifier(DoggoModOverhauled.MODID, "textures/entity/doggo/doggo_collar.png");

    public DoggoCollarFeatureRenderer(FeatureRendererContext<DoggoEntity, DoggoEntityModel<DoggoEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, DoggoEntity doggoEntity, float f, float g, float h, float j, float k, float l) {
        if (doggoEntity.isTamed() && !doggoEntity.isInvisible()) {
            float[] fs = doggoEntity.getCollarColor().getColorComponents();
            renderModel(this.getContextModel(), SKIN, matrixStack, vertexConsumerProvider, i, doggoEntity, fs[0], fs[1], fs[2]);
        }
    }
}
