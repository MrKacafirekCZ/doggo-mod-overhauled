package net.lordmrk.dmo.client.render.entity.model;

import com.google.common.collect.ImmutableList;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.TintableAnimalModel;
import net.minecraft.util.math.MathHelper;

public class DoggoEntityModel<T extends DoggoEntity> extends TintableAnimalModel<T> {

	/**
	 * The key of the real head model part, whose value is {@value}.
	 */
	private static final String REAL_HEAD = "real_head";
	/**
	 * The key of the upper body model part, whose value is {@value}.
	 */
	private static final String UPPER_BODY = "upper_body";
	/**
	 * The key of the real tail model part, whose value is {@value}.
	 */
	private static final String REAL_TAIL = "real_tail";
	/**
	 * The main bone used to animate the head. Contains {@link #realHead} as one of
	 * its children.
	 */
	public final ModelPart head;
	protected final ModelPart realHead;
	protected final ModelPart torso;
	protected final ModelPart rightHindLeg;
	protected final ModelPart leftHindLeg;
	protected final ModelPart rightFrontLeg;
	protected final ModelPart leftFrontLeg;
	/**
	 * The main bone used to animate the tail. Contains {@link #realTail} as one of
	 * its children.
	 */
	protected final ModelPart tail;
	protected final ModelPart realTail;
	protected final ModelPart neck;
	protected final ModelPart mouth;

	protected static final float DEGREES_1   = 0.01745329f;
	protected static final float DEGREES_5   = 0.08726646f;
	protected static final float DEGREES_10  = 0.17453292f;
	protected static final float DEGREES_15  = 0.26179938f;
	protected static final float DEGREES_20  = 0.34906585f;
	protected static final float DEGREES_25  = 0.43633231f;
	protected static final float DEGREES_30  = 0.52359877f;
	protected static final float DEGREES_35  = 0.61086523f;
	protected static final float DEGREES_40  = 0.69813170f;
	protected static final float DEGREES_45  = 0.78539816f;
	protected static final float DEGREES_50  = 0.87266462f;
	protected static final float DEGREES_55  = 0.95993108f;
	protected static final float DEGREES_60  = 1.04719755f;
	protected static final float DEGREES_65  = 1.13446401f;
	protected static final float DEGREES_70  = 1.22173047f;
	protected static final float DEGREES_72  = 1.25663706f;
	protected static final float DEGREES_75  = 1.30899694f;
	protected static final float DEGREES_80  = 1.39626340f;
	protected static final float DEGREES_85  = 1.48352986f;
	protected static final float DEGREES_90  = 1.57079632f;
	protected static final float DEGREES_95  = 1.65806279f;
	protected static final float DEGREES_100 = 1.74532925f;
	protected static final float DEGREES_105 = 1.83259571f;
	protected static final float DEGREES_110 = 1.91986217f;
	protected static final float DEGREES_115 = 2.00712864f;
	protected static final float DEGREES_120 = 2.09439510f;
	protected static final float DEGREES_125 = 2.18166156f;
	protected static final float DEGREES_130 = 2.26892802f;
	protected static final float DEGREES_180 = 3.14159265f;
	protected static final float DEGREES_270 = 4.71238898f;
	protected static final float DEGREES_333 = 5.81194641f;

	public DoggoEntityModel(ModelPart root) {
		this.head = root.getChild(EntityModelPartNames.HEAD);
	    this.realHead = this.head.getChild("real_head");
		this.mouth = this.realHead.getChild("doggo_mouth");
	    this.torso = root.getChild(EntityModelPartNames.BODY);
	    this.neck = root.getChild("upper_body");
	    this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
	    this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
	    this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
	    this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
	    this.tail = root.getChild(EntityModelPartNames.TAIL);
	    this.realTail = this.tail.getChild("real_tail");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		// Head
		ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 13.5F, -7.0F));
		modelPartData2.addChild("real_head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).uv(16, 14).cuboid(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).uv(16, 14).cuboid(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).uv(0, 10).cuboid(-0.5F, 0.0F, -5.0F, 3.0F, 2.0F, 4.0F), ModelTransform.NONE)
			.addChild("doggo_mouth", ModelPartBuilder.create().uv(43, 14).cuboid(-0.5F, 0.0F, -5.0F, 3.0F, 1.0F, 4.0F), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

		// Body
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(18, 14).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), ModelTransform.of(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
		modelPartData.addChild("upper_body", ModelPartBuilder.create().uv(21, 0).cuboid(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), ModelTransform.of(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));

		// Legs
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-2.5F, 16.0F, 7.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(0.5F, 16.0F, 7.0F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-2.5F, 16.0F, -4.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(0.5F, 16.0F, -4.0F));

		// Tail
		ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create(), ModelTransform.of(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
		modelPartData3.addChild("real_tail", ModelPartBuilder.create().uv(9, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.NONE);

		return TexturedModelData.of(modelData, 64, 32);
	}

	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.torso, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
	}

	public void setAngles(T doggoEntity, float f, float g, float h, float i, float j) {
		switch(doggoEntity.getAction()) {
		case NAPPING:
			return;
		case EATING_FROM_BOWL:
			this.head.pitch = 0.69813170f;
			this.head.yaw = i * 0.017453292F;
			break;
		case EATING:
		case NEUTRAL:
		case PLAY_TIME:
			this.head.pitch = j * 0.017453292F;
			this.head.yaw = i * 0.017453292F;
		}

		tail(doggoEntity, h);
		setMouth(doggoEntity);
	}

	private void setMouth(T doggoEntity) {
		if(doggoEntity.hasMouthOpened() || doggoEntity.hasStackInMouth()) {
			this.mouth.pitch = 0.17453292f;
		} else {
			this.mouth.pitch = 0f;
		}
	}
	
	private void tail(DoggoEntity doggoEntity, float h) {
		if(!doggoEntity.isInSittingPose() && !doggoEntity.isAction(DoggoAction.LISTENING) && !doggoEntity.isAction(DoggoAction.SCRATCHING)) {
			h += (this.torso.pitch - 1.57079632f);
		}

		switch(doggoEntity.getFeeling()) {
		case NEUTRAL:
			this.tail.yaw = 0f;
			this.tail.pitch = h;
			return;
		case HAPPY:
			this.tail.yaw = MathHelper.cos(doggoEntity.getAnimationTick()) * 0.4f;
			this.tail.pitch = h;
			return;
		case SAD:
			this.tail.yaw = 0f;
			this.tail.pitch = 40f;
			return;
		}
	}
}
