package net.lordmrk.dmo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.lordmrk.dmo.screen.DogBowlScreenHandler;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoggoModOverhauled implements ModInitializer {

	public static final String MODID = "doggomodoverhauled";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Identifier DOG_BOWL = new Identifier(MODID, "dog_bowl_entity");
	public static BlockEntityType<DogBowlEntity> DOG_BOWL_ENTITY;
	public static final ScreenHandlerType<DogBowlScreenHandler> DOG_BOWL_SCREEN_HANDLER;


	@Override
	public void onInitialize() {
		DoggoEntities.registerAll();
		DoggoBlocks.registerAll();
		DoggoItems.registerAll();
		DoggoItemGroups.registerAll();

		DoggoBlocks.addToColoredBlocksItemGroup();
		DoggoBlocks.addToDoggoModItemGroup();
		DoggoBlocks.addToFunctionalBlocksItemGroup();

		DoggoItems.addToToolItemsItemGroup();
		DoggoItems.addToDoggoModItemGroup();

		DOG_BOWL_ENTITY = Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				DOG_BOWL,
				FabricBlockEntityTypeBuilder.create(
						DogBowlEntity::new,
						DoggoBlocks.getDogBowls()).build(null));

		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_ACTION);
		TrackedDataHandlerRegistry.register(TrackedDoggoData.DOGGO_FEELING);
	}

	static {
		DOG_BOWL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(DOG_BOWL, DogBowlScreenHandler::new);
	}
}
