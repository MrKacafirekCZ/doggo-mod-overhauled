package net.lordmrk.dmo.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.lordmrk.dmo.DoggoEntities;
import net.lordmrk.dmo.entity.projectile.thrown.AbstractTennisBallEntity;
import net.lordmrk.dmo.entity.projectile.thrown.RedTennisBallEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TennisBall extends Item {

    private final DyeColor color;
    private final boolean legacy;

    public TennisBall(DyeColor color, boolean legacy) {
        super(new FabricItemSettings().maxCount(16));

        this.color = color;
        this.legacy = legacy;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(this.legacy) {
            tooltip.add(Text.literal("This version of tennis ball is deprecated and will be removed in the future.").formatted(Formatting.RED));
            tooltip.add(Text.literal("Use a crafting table to convert it into up-to-date tennis ball.").formatted(Formatting.RED));
        }
    }

    public DyeColor getColor() {
        return color;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(this.legacy) {
            return TypedActionResult.fail(itemStack);
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            AbstractTennisBallEntity tennisBallEntity = DoggoEntities.newTennisBall(world, user, this.getColor());
            tennisBallEntity.setItem(itemStack);
            tennisBallEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(tennisBallEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
