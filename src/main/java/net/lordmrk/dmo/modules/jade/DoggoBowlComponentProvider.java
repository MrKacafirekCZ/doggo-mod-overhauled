package net.lordmrk.dmo.modules.jade;

import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DoggoBowlComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    private static final String KEY_CUSTOM_NAME = "CustomName";

    @Override
    public void appendServerData(NbtCompound nbt, BlockAccessor accessor) {
        DogBowlEntity dogBowl = (DogBowlEntity) accessor.getBlockEntity();

        if (dogBowl.getCustomName() != null) {
            nbt.putString(KEY_CUSTOM_NAME, dogBowl.getCustomName().getString());
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        NbtCompound nbt = accessor.getServerData();

        tooltip.clear();
        if (nbt.contains(KEY_CUSTOM_NAME)) {
            tooltip.append(Text.translatable("jade.doggomodoverhauled.dog_bowl.custom_name", nbt.getString(KEY_CUSTOM_NAME)).fillStyle(Style.EMPTY.withColor(Formatting.WHITE)));
        } else {
            tooltip.append(Text.translatable(accessor.getBlockState().getBlock().getTranslationKey()).fillStyle(Style.EMPTY.withColor(Formatting.WHITE)));
        }
    }

    @Override
    public Identifier getUid() {
        return DoggoModOverhauled.JADE_DOG_BOWL;
    }
}
