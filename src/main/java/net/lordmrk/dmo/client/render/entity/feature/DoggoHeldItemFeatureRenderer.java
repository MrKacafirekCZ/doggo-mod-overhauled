package net.lordmrk.dmo.client.render.entity.feature;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.client.render.entity.model.DoggoEntityModel;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class DoggoHeldItemFeatureRenderer extends FeatureRenderer<DoggoEntity, DoggoEntityModel<DoggoEntity>> {
	
	public DoggoHeldItemFeatureRenderer(FeatureRendererContext<DoggoEntity, DoggoEntityModel<DoggoEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	@SuppressWarnings("rawtypes")
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, DoggoEntity doggoEntity, float f, float g, float h, float j, float k, float l) {
		if(doggoEntity.hasStackInMouth()) {
			boolean bl = doggoEntity.isBaby();
			matrixStack.push();
			float n = 0f;
			if(bl) {
				n = 0.75F;
				matrixStack.scale(0.75F, 0.75F, 0.75F);
				matrixStack.translate(0.0D, 0.5D, 0.20937499403953552D);
			}

			matrixStack.translate(this.getContextModel().head.pivotX / 16.0F, this.getContextModel().head.pivotY / 16.0F, this.getContextModel().head.pivotZ / 16.0F);
			n = doggoEntity.getBegAnimationProgress(h) + doggoEntity.getShakeAnimationProgress(h, 0.0F);
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(n));
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(k));
			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(l));
			if(doggoEntity.isBaby()) {
				matrixStack.translate(0.05999999865889549D, 0.25999999046325684D, -0.5D);
			} else if(doggoEntity.isAction(DoggoAction.NAPPING)) {
				matrixStack.translate(-0.28, 0.155, -0.3);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(50.0F));
			} else {
				matrixStack.translate(-n * 0.1, 0.155, -0.38);
			}

			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-30.0F));

			ItemStack itemStack = doggoEntity.getStackInMouth();
			MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.GROUND, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, doggoEntity.getWorld(), doggoEntity.getId());
			matrixStack.pop();
		}
	}
}
