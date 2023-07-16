package net.lordmrk.dmo.modules.rei;

//public class ReiPlugin {}

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.lordmrk.dmo.DoggoModOverhauled;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ReiPlugin implements REIClientPlugin {

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }

    private void registerDescriptions(DisplayRegistry registry) {
        addDescriptionToDogBowls(registry,
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.1"),
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.2"),
                Text.translatable("rei.doggomodoverhauled.dog_bowl.description.3"));
        addDescription(registry, DoggoModOverhauled.TENNIS_BALL,
                Text.translatable("rei.doggomodoverhauled.tennis_ball.description.1"),
                Text.translatable("rei.doggomodoverhauled.tennis_ball.description.2"));
    }

    private void addDescriptionToDogBowls(DisplayRegistry registry, Text... lines) {
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_WHITE, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_LIGHT_GRAY, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_GRAY, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_BLACK, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_BROWN, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_RED, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_ORANGE, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_YELLOW, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_LIME, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_GREEN, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_CYAN, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_LIGHT_BLUE, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_BLUE, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_PURPLE, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_MAGENTA, lines);
        addDescription(registry, DoggoModOverhauled.DOG_BOWL_PINK, lines);
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