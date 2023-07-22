package net.lordmrk.dmo;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class DoggoItemTags {

    public static final TagKey<Item> DOG_BED = of("dog_bed");
    public static final TagKey<Item> DOG_BOWL = of("dog_bowl");
    public static final TagKey<Item> TENNIS_BALL = of("tennis_ball");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(id));
    }
}
