package net.lordmrk.dmo;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.lordmrk.dmo.entity.projectile.thrown.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DoggoEntities {

    public static EntityType<RedTennisBallEntity> TENNIS_BALL_RED_ENTITY;
    public static EntityType<YellowTennisBallEntity> TENNIS_BALL_YELLOW_ENTITY;
    public static EntityType<LimeTennisBallEntity> TENNIS_BALL_LIME_ENTITY;
    public static EntityType<LightBlueTennisBallEntity> TENNIS_BALL_LIGHT_BLUE_ENTITY;

    public static EntityType<DoggoEntity> DOGGO_ENTITY;

    public static AbstractTennisBallEntity newTennisBall(World world, PlayerEntity user, DyeColor color) {
        switch(color) {
            case RED:
                return new RedTennisBallEntity(world, user);
            case YELLOW:
                return new YellowTennisBallEntity(world, user);
            case LIME:
                return new LimeTennisBallEntity(world, user);
            case LIGHT_BLUE:
                return new LightBlueTennisBallEntity(world, user);
            default:
                throw new IllegalArgumentException("No tennis ball entity found for color \"" + color.getName() + "\"");
        }
    }

    public static void registerAll() {
        DOGGO_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(DoggoModOverhauled.MODID, "doggo"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DoggoEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 0.85F))
                        .build());

        TENNIS_BALL_RED_ENTITY = registerTennisBall(DyeColor.RED, RedTennisBallEntity::new);
        TENNIS_BALL_YELLOW_ENTITY = registerTennisBall(DyeColor.YELLOW, YellowTennisBallEntity::new);
        TENNIS_BALL_LIME_ENTITY = registerTennisBall(DyeColor.LIME, LimeTennisBallEntity::new);
        TENNIS_BALL_LIGHT_BLUE_ENTITY = registerTennisBall(DyeColor.LIGHT_BLUE, LightBlueTennisBallEntity::new);

        FabricDefaultAttributeRegistry.register(DOGGO_ENTITY, DoggoEntity.createDoggoAttributes());
    }

    private static <T extends AbstractTennisBallEntity> EntityType<T> registerTennisBall(DyeColor color, EntityType.EntityFactory<T> entityFactory) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(DoggoModOverhauled.MODID, "tennis_ball_" + color.getName() + "_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, entityFactory)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeBlocks(4).trackedUpdateRate(10).build());
    }
}
