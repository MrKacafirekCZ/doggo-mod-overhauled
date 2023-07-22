package net.lordmrk.dmo;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lordmrk.dmo.block.DogBed;
import net.lordmrk.dmo.block.DogBowl;
import net.lordmrk.dmo.item.DogBedItem;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class DoggoBlocks {

    private static final DyeColor[] COLORS = new DyeColor[] {
            DyeColor.WHITE,
            DyeColor.LIGHT_GRAY,
            DyeColor.GRAY,
            DyeColor.BLACK,
            DyeColor.BROWN,
            DyeColor.RED,
            DyeColor.ORANGE,
            DyeColor.YELLOW,
            DyeColor.LIME,
            DyeColor.GREEN,
            DyeColor.CYAN,
            DyeColor.LIGHT_BLUE,
            DyeColor.BLUE,
            DyeColor.PURPLE,
            DyeColor.MAGENTA,
            DyeColor.PINK
    };

    public static final List<AbstractMap.SimpleEntry<String, Block>> BED_LIST = new ArrayList<>();
    public static final List<AbstractMap.SimpleEntry<String, Block>> BOWL_LIST = new ArrayList<>();

    private static void addToItemGroup(RegistryKey<ItemGroup> registryKey, List<AbstractMap.SimpleEntry<String, Block>> blockList, ItemConvertible addAfter) {
        ItemGroupEvents.modifyEntriesEvent(registryKey).register(content -> {
            ItemConvertible lastItem = addAfter;

            for(AbstractMap.SimpleEntry<String, Block> entry : blockList) {
                if(lastItem == null) {
                    content.add(entry.getValue());
                } else {
                    content.addAfter(lastItem, entry.getValue());
                }

                lastItem = entry.getValue();
            }
        });
    }

    public static void addToColoredBlocksItemGroup() {
        RegistryKey<ItemGroup> registryKey = ItemGroups.COLORED_BLOCKS;

        addToItemGroup(registryKey, BED_LIST, Items.PINK_BANNER);
        addToItemGroup(registryKey, BOWL_LIST, BED_LIST.get(BED_LIST.size() - 1).getValue());
    }

    public static void addToDoggoModItemGroup() {
        RegistryKey<ItemGroup> registryKey = DoggoItemGroups.DOG_STUFF_REGISTRY_KEY;

        addToItemGroup(registryKey, BED_LIST, Items.PINK_BANNER);
        addToItemGroup(registryKey, BOWL_LIST, BED_LIST.get(BED_LIST.size() - 1).getValue());
    }

    public static void addToFunctionalBlocksItemGroup() {
        RegistryKey<ItemGroup> registryKey = ItemGroups.FUNCTIONAL;

        addToItemGroup(registryKey, BED_LIST, Items.PINK_BANNER);
        addToItemGroup(registryKey, BOWL_LIST, BED_LIST.get(BED_LIST.size() - 1).getValue());
    }

    @NotNull
    public static DogBowl getDogBowl(DyeColor color) {
        for(DogBowl dogBowl : getDogBowls()) {
            if(dogBowl.getColor() == color) {
                return dogBowl;
            }
        }

        throw new IllegalArgumentException("No dog bowl found for color \"" + color.getName() + "\"");
    }

    public static DogBowl[] getDogBowls() {
        DogBowl[] dogBowls = new DogBowl[BOWL_LIST.size()];

        for(int i = 0; i < BOWL_LIST.size(); i++) {
            dogBowls[i] = (DogBowl) BOWL_LIST.get(i).getValue();
        }

        return dogBowls;
    }

    public static void registerAll() {
        for(AbstractMap.SimpleEntry<String, Block> entry : BED_LIST) {
            registerBlock(entry.getKey(), entry.getValue());
        }

        for(AbstractMap.SimpleEntry<String, Block> entry : BOWL_LIST) {
            registerBlock(entry.getKey(), entry.getValue());
        }
    }

    private static void registerBlock(String id, Block block) {
        Registry.register(Registries.BLOCK, new Identifier(DoggoModOverhauled.MODID, id), block);

        if(block instanceof DogBed) {
            Registry.register(Registries.ITEM, new Identifier(DoggoModOverhauled.MODID, id), new DogBedItem(block));
            return;
        }

        Registry.register(Registries.ITEM, new Identifier(DoggoModOverhauled.MODID, id), new BlockItem(block, new FabricItemSettings()));
    }

    static {
        for(WoodType woodType : WoodType.stream().toList()) {
            for(DyeColor color : COLORS) {
                BED_LIST.add(new AbstractMap.SimpleEntry<>("dog_bed_" + woodType.name() + "_" + color.getName(), new DogBed(color, woodType)));
            }
        }

        for(DyeColor color : COLORS) {
            BOWL_LIST.add(new AbstractMap.SimpleEntry<>("dog_bowl_" + color.getName(), new DogBowl(color)));
        }
    }
}
