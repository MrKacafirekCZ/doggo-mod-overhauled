package net.lordmrk.dmo;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class DoggoItemGroups {

    public static final RegistryKey<ItemGroup> DOG_STUFF_REGISTRY_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(DoggoModOverhauled.MODID, "doggomodoverhauled"));
    public static final ItemGroup DOG_STUFF_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + DoggoModOverhauled.MODID + ".doggomodoverhauled"))
            .icon(() -> new ItemStack(DoggoBlocks.getDogBowl(DyeColor.CYAN)))
            .build();

    public static void registerAll() {
        registerItemGroup("doggomodoverhauled", DOG_STUFF_GROUP);
    }

    private static void registerItemGroup(String id, ItemGroup itemGroup) {
        Registry.register(Registries.ITEM_GROUP, new Identifier(DoggoModOverhauled.MODID, id), itemGroup);
    }
}
