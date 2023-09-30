package net.lordmrk.dmo.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DogBedItem extends BlockItem {

    //private final Text name;

    public DogBedItem(Block block) {
        super(block, new FabricItemSettings());

        //this.name = block.getName();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Decoration only for the time being.").formatted(Formatting.RED));
    }
/*
    @Override
    public Text getName() {
        return name;
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return name;
    }*/
}
