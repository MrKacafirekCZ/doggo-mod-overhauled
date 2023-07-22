package net.lordmrk.dmo.entity.projectile.thrown;

import net.lordmrk.dmo.DoggoEntities;
import net.lordmrk.dmo.DoggoItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

public class YellowTennisBallEntity extends AbstractTennisBallEntity {

    public YellowTennisBallEntity(EntityType<? extends AbstractTennisBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public YellowTennisBallEntity(World world, LivingEntity owner) {
        super(DoggoEntities.TENNIS_BALL_YELLOW_ENTITY, world, owner);
    }

    @Override
    protected Item getDefaultItem() {
        return DoggoItems.getTennisBall(DyeColor.YELLOW);
    }
}
