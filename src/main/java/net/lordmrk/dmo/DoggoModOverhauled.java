package net.lordmrk.dmo;

import me.shedaniel.rei.api.common.plugins.REIPluginProvider;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.lordmrk.dmo.block.DogBowl;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.lordmrk.dmo.entity.projectile.thrown.TennisBallEntity;
import net.lordmrk.dmo.item.TennisBall;
import net.lordmrk.dmo.screen.DogBowlScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoggoModOverhauled implements ModInitializer {

	public static final String MODID = "doggomodoverhauled";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Block DOG_BOWL_WHITE = new DogBowl();
	public static final Block DOG_BOWL_LIGHT_GRAY = new DogBowl();
	public static final Block DOG_BOWL_GRAY = new DogBowl();
	public static final Block DOG_BOWL_BLACK = new DogBowl();
	public static final Block DOG_BOWL_BROWN = new DogBowl();
	public static final Block DOG_BOWL_RED = new DogBowl();
	public static final Block DOG_BOWL_ORANGE = new DogBowl();
	public static final Block DOG_BOWL_YELLOW = new DogBowl();
	public static final Block DOG_BOWL_LIME = new DogBowl();
	public static final Block DOG_BOWL_GREEN = new DogBowl();
	public static final Block DOG_BOWL_CYAN = new DogBowl();
	public static final Block DOG_BOWL_LIGHT_BLUE = new DogBowl();
	public static final Block DOG_BOWL_BLUE = new DogBowl();
	public static final Block DOG_BOWL_PURPLE = new DogBowl();
	public static final Block DOG_BOWL_MAGENTA = new DogBowl();
	public static final Block DOG_BOWL_PINK = new DogBowl();

	public static final EntityType<DoggoEntity> DOGGO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MODID, "doggo"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DoggoEntity::new)
					.dimensions(EntityDimensions.fixed(0.6F, 0.85F))
					.build());

	public static final Item TENNIS_BALL = new TennisBall();
	public static EntityType<TennisBallEntity> TENNIS_BALL_ENTITY;

	public static final Identifier DOG_BOWL = new Identifier(MODID, "dog_bowl_entity");
	public static BlockEntityType<DogBowlEntity> DOG_BOWL_ENTITY;
	public static final ScreenHandlerType<DogBowlScreenHandler> DOG_BOWL_SCREEN_HANDLER;

	public static final ItemGroup DOG_STUFF_GROUP = FabricItemGroup.builder(
			new Identifier(MODID, "doggomodoverhauled"))
			.icon(() -> new ItemStack(DOG_BOWL_CYAN))
			.build();

	@Override
	public void onInitialize() {
		registerBlockAndItem("dog_bowl_white", DOG_BOWL_WHITE);
		registerBlockAndItem("dog_bowl_light_gray", DOG_BOWL_LIGHT_GRAY);
		registerBlockAndItem("dog_bowl_gray", DOG_BOWL_GRAY);
		registerBlockAndItem("dog_bowl_black", DOG_BOWL_BLACK);
		registerBlockAndItem("dog_bowl_brown", DOG_BOWL_BROWN);
		registerBlockAndItem("dog_bowl_red", DOG_BOWL_RED);
		registerBlockAndItem("dog_bowl_orange", DOG_BOWL_ORANGE);
		registerBlockAndItem("dog_bowl_yellow", DOG_BOWL_YELLOW);
		registerBlockAndItem("dog_bowl_lime", DOG_BOWL_LIME);
		registerBlockAndItem("dog_bowl_green", DOG_BOWL_GREEN);
		registerBlockAndItem("dog_bowl_cyan", DOG_BOWL_CYAN);
		registerBlockAndItem("dog_bowl_light_blue", DOG_BOWL_LIGHT_BLUE);
		registerBlockAndItem("dog_bowl_blue", DOG_BOWL_BLUE);
		registerBlockAndItem("dog_bowl_purple", DOG_BOWL_PURPLE);
		registerBlockAndItem("dog_bowl_magenta", DOG_BOWL_MAGENTA);
		registerBlockAndItem("dog_bowl_pink", DOG_BOWL_PINK);

		registerItem("tennis_ball", TENNIS_BALL);

		addBowlsToItemGroup(ItemGroups.COLORED_BLOCKS, Items.PINK_BANNER);
		addBowlsToItemGroup(ItemGroups.FUNCTIONAL, Items.PINK_BANNER);
		addTennisBallToItemGroup(ItemGroups.TOOLS, Items.ENDER_EYE);
		addBowlsToItemGroup(DOG_STUFF_GROUP, null);
		addTennisBallToItemGroup(DOG_STUFF_GROUP, null);

		DOG_BOWL_ENTITY = Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				DOG_BOWL,
				FabricBlockEntityTypeBuilder.create(
						DogBowlEntity::new,
						DOG_BOWL_WHITE, DOG_BOWL_LIGHT_GRAY, DOG_BOWL_GRAY, DOG_BOWL_BLACK,
						DOG_BOWL_BROWN, DOG_BOWL_RED, DOG_BOWL_ORANGE, DOG_BOWL_YELLOW,
						DOG_BOWL_LIME, DOG_BOWL_GREEN, DOG_BOWL_CYAN, DOG_BOWL_LIGHT_BLUE,
						DOG_BOWL_BLUE, DOG_BOWL_PURPLE, DOG_BOWL_MAGENTA, DOG_BOWL_PINK).build(null));

		TENNIS_BALL_ENTITY = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(MODID, "tennis_ball_entity"),
				FabricEntityTypeBuilder.<TennisBallEntity>create(SpawnGroup.MISC, TennisBallEntity::new)
						.dimensions(EntityDimensions.fixed(0.25F, 0.25F))
						.trackRangeBlocks(4).trackedUpdateRate(10).build());

		FabricDefaultAttributeRegistry.register(DOGGO, DoggoEntity.createDoggoAttributes());

		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_ACTION);
		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_FEELING);
	}

	private void addBowlsToItemGroup(ItemGroup group, Item addAfter) {
		ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
			if(addAfter == null) {
				content.add(DOG_BOWL_WHITE);
			} else {
				content.addAfter(addAfter, DOG_BOWL_WHITE);
			}

			content.addAfter(DOG_BOWL_WHITE, DOG_BOWL_LIGHT_GRAY);
			content.addAfter(DOG_BOWL_LIGHT_GRAY, DOG_BOWL_GRAY);
			content.addAfter(DOG_BOWL_GRAY, DOG_BOWL_BLACK);
			content.addAfter(DOG_BOWL_BLACK, DOG_BOWL_BROWN);
			content.addAfter(DOG_BOWL_BROWN, DOG_BOWL_RED);
			content.addAfter(DOG_BOWL_RED, DOG_BOWL_ORANGE);
			content.addAfter(DOG_BOWL_ORANGE, DOG_BOWL_YELLOW);
			content.addAfter(DOG_BOWL_YELLOW, DOG_BOWL_LIME);
			content.addAfter(DOG_BOWL_LIME, DOG_BOWL_GREEN);
			content.addAfter(DOG_BOWL_GREEN, DOG_BOWL_CYAN);
			content.addAfter(DOG_BOWL_CYAN, DOG_BOWL_LIGHT_BLUE);
			content.addAfter(DOG_BOWL_LIGHT_BLUE, DOG_BOWL_BLUE);
			content.addAfter(DOG_BOWL_BLUE, DOG_BOWL_PURPLE);
			content.addAfter(DOG_BOWL_PURPLE, DOG_BOWL_MAGENTA);
			content.addAfter(DOG_BOWL_MAGENTA, DOG_BOWL_PINK);
		});
	}

	private void addTennisBallToItemGroup(ItemGroup group, Item addAfter) {
		ItemGroupEvents.modifyEntriesEvent(group).register(content -> {
			if(addAfter == null) {
				content.add(TENNIS_BALL);
			} else {
				content.addAfter(addAfter, TENNIS_BALL);
			}
		});
	}

	private void registerBlockAndItem(String id, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(MODID, id), block);
		registerItem(id, new BlockItem(block, new FabricItemSettings()));
	}

	private void registerItem(String id, Item item) {
		Registry.register(Registries.ITEM, new Identifier(MODID, id), item);
	}

	static {
		DOG_BOWL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(DOG_BOWL, DogBowlScreenHandler::new);
	}
}
