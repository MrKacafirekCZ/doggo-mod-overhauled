package net.lordmrk.dmo.mixin;

import net.lordmrk.dmo.DoggoEntities;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public class WolfEntityMixin extends TameableEntity {

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/entity/passive/WolfEntity;setOwner(Lnet/minecraft/entity/player/PlayerEntity;)V")
            , cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        DoggoEntity doggoEntity = DoggoEntities.DOGGO_ENTITY.spawn((ServerWorld)this.getWorld(), null/*, this.getCustomName()*/, null, this.getBlockPos(), SpawnReason.CONVERSION, true, false);
        doggoEntity.setOwner(player);
        this.getWorld().sendEntityStatus(doggoEntity, (byte)7);
        this.remove(RemovalReason.DISCARDED);

        ci.cancel();
        ci.setReturnValue(ActionResult.SUCCESS);
    }

    @Override
    public EntityView method_48926() {
        return null;
    }
}
