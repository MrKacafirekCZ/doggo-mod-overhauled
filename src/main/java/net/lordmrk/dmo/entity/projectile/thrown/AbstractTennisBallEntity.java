package net.lordmrk.dmo.entity.projectile.thrown;

import net.lordmrk.dmo.DoggoEntities;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTennisBallEntity extends ThrownItemEntity {

    private static final Set<Block> BREAKABLE_BLOCKS = new HashSet<>();

    public AbstractTennisBallEntity(EntityType<? extends AbstractTennisBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractTennisBallEntity(EntityType<? extends AbstractTennisBallEntity> entityType, World world, LivingEntity owner) {
        super(entityType, owner, world);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if(!this.getWorld().isClient) {
			if(hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = new BlockPos((int) hitResult.getPos().getX(), (int) hitResult.getPos().getY(), (int) hitResult.getPos().getZ());
				BlockState state = this.getWorld().getBlockState(pos);

				if(BREAKABLE_BLOCKS.contains(state.getBlock())) {
					this.getWorld().breakBlock(pos, false);
				}
			}

            ItemScatterer.spawn(this.getWorld(), this.getX(), this.getY(), this.getZ(), this.getDefaultItem().getDefaultStack());

            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    static {
        BREAKABLE_BLOCKS.add(Blocks.GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.WHITE_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.GRAY_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.BLACK_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.BROWN_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.RED_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.ORANGE_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.YELLOW_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.LIME_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.GREEN_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.CYAN_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.BLUE_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.PURPLE_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.MAGENTA_STAINED_GLASS_PANE);
        BREAKABLE_BLOCKS.add(Blocks.PINK_STAINED_GLASS_PANE);
    }
}
