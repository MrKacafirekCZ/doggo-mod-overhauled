package net.lordmrk.dmo.entity.projectile.thrown;

import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TennisBallEntity extends ThrownItemEntity {

    public TennisBallEntity(EntityType<? extends TennisBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public TennisBallEntity(World world, LivingEntity owner) {
        super(DoggoModOverhauled.TENNIS_BALL_ENTITY, owner, world);
    }

    public TennisBallEntity(World world, double x, double y, double z) {
        super(DoggoModOverhauled.TENNIS_BALL_ENTITY, x, y, z, world);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!this.world.isClient) {
			if(hitResult.getType() == HitResult.Type.BLOCK) {
				BlockState state = world.getBlockState(new BlockPos(hitResult.getPos()));

                // I have a feeling there is a better way to do this, but I just don't know it.
				if(state.getBlock().equals(Blocks.GLASS_PANE) ||
                        state.getBlock().equals(Blocks.WHITE_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.GRAY_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.BLACK_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.BROWN_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.RED_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.ORANGE_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.YELLOW_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.LIME_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.GREEN_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.CYAN_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.BLUE_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.PURPLE_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.MAGENTA_STAINED_GLASS_PANE) ||
                        state.getBlock().equals(Blocks.PINK_STAINED_GLASS_PANE)) {
					world.breakBlock(new BlockPos(hitResult.getPos()), false);
				}
			}

            ItemScatterer.spawn(world, getX(), getY(), getZ(), getDefaultItem().getDefaultStack());

            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return DoggoModOverhauled.TENNIS_BALL;
    }
}
