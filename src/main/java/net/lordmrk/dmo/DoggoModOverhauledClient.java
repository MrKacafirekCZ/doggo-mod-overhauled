package net.lordmrk.dmo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.lordmrk.dmo.client.gui.screen.ingame.DogBowlScreen;
import net.lordmrk.dmo.client.render.block.entity.DogBowlBlockEntityRenderer;
import net.lordmrk.dmo.client.render.entity.DoggoEntityRenderer;
import net.lordmrk.dmo.client.render.entity.model.DoggoEntityModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class DoggoModOverhauledClient implements ClientModInitializer {

    public static final EntityModelLayer DOGGO = new EntityModelLayer(new Identifier(DoggoModOverhauled.MODID, "doggo"), "main");
    public static final Identifier PacketID = new Identifier(DoggoModOverhauled.MODID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(DoggoEntities.DOGGO_ENTITY, DoggoEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(DOGGO, DoggoEntityModel::getTexturedModelData);

        BlockEntityRendererRegistry.register(DoggoModOverhauled.DOG_BOWL_ENTITY, DogBowlBlockEntityRenderer::new);
        ScreenRegistry.register(DoggoModOverhauled.DOG_BOWL_SCREEN_HANDLER, DogBowlScreen::new);

        EntityRendererRegistry.register(DoggoEntities.TENNIS_BALL_RED_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(DoggoEntities.TENNIS_BALL_YELLOW_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(DoggoEntities.TENNIS_BALL_LIME_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(DoggoEntities.TENNIS_BALL_LIGHT_BLUE_ENTITY, FlyingItemEntityRenderer::new);

        receiveEntityPacket();
    }

    private void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID, (client, handler, byteBuf, sender) -> {
            EntityType<?> et = Registries.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);

            if (MinecraftClient.getInstance().world == null)
                throw new IllegalStateException("Tried to spawn entity in a null world!");
            Entity entity = et.create(MinecraftClient.getInstance().world);
            if (entity == null)
                throw new IllegalStateException("Failed to create instance of entity \"" + Registries.ENTITY_TYPE.getId(et) + "\"!");
            entity.updateTrackedPosition(pos.x, pos.y, pos.z);
            entity.setPos(pos.x, pos.y, pos.z);
            entity.setPitch(pitch);
            entity.setYaw(yaw);
            entity.setId(entityId);
            entity.setUuid(uuid);
            MinecraftClient.getInstance().world.addEntity(entityId, entity);
        });
    }
}
