package net.lordmrk.dmo.modules.rei;

//public class ReiPlugin {}

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.lordmrk.dmo.DoggoBlocks;
import net.lordmrk.dmo.DoggoItems;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.AbstractMap;

public class ReiPlugin implements REIClientPlugin {

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }

    private void registerDescriptions(DisplayRegistry registry) {
        addDescriptionToDogBeds(registry,
                Text.translatable("rei.doggomodoverhauled.dog_bed.description.1"));
        addDescriptionToDogBowls(registry,
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.1"),
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.2"),
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.3"));
        addDescriptionToDogToys(registry,
                Text.translatable("rei.doggomodoverhauled.tennis_ball.description.1"),
                Text.translatable("rei.doggomodoverhauled.tennis_ball.description.2"));
    }

    private void addDescriptionToDogBeds(DisplayRegistry registry, Text... lines) {
        for(AbstractMap.SimpleEntry<String, Block> entry : DoggoBlocks.BED_LIST) {
            addDescription(registry, entry.getValue(), lines);
        }
    }

    private void addDescriptionToDogBowls(DisplayRegistry registry, Text... lines) {
        for(AbstractMap.SimpleEntry<String, Block> entry : DoggoBlocks.BOWL_LIST) {
            addDescription(registry, entry.getValue(), lines);
        }
    }

    private void addDescriptionToDogToys(DisplayRegistry registry, Text... lines) {
        for(AbstractMap.SimpleEntry<String, Item> entry : DoggoItems.TOY_LIST) {
            addDescription(registry, entry.getValue(), lines);
        }
    }

    private void addDescription(DisplayRegistry registry, Block block, Text... lines) {
        addDescription(registry, block.asItem(), lines);
    }

    private void addDescription(DisplayRegistry registry, Item item, Text... lines) {
        ItemStack itemStack = item.getDefaultStack();
        DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(itemStack), itemStack.getName());

        info.lines(lines);
        registry.add(info);
    }
}