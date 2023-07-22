package net.lordmrk.dmo;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class DoggoBlockTags {

    public static final TagKey<Block> DOG_BED = of("dog_bed");
    public static final TagKey<Block> DOG_BOWL = of("dog_bowl");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(id));
    }
}
