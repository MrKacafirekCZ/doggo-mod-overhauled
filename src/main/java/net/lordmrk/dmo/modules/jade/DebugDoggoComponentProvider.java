package net.lordmrk.dmo.modules.jade;

import net.lordmrk.dmo.DoggoAction;
import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DebugDoggoComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {

    INSTANCE;

    private static Identifier identifier = new Identifier("debug");
    private static String KEY_ACTION = "Action";
    private static String KEY_GOAL_DELAY = "GoalDelay";

    @Override
    public void appendServerData(NbtCompound nbt, EntityAccessor entityAccessor) {
        DoggoEntity doggoEntity = (DoggoEntity) entityAccessor.getEntity();
        nbt.putString(KEY_ACTION, doggoEntity.getAction().name());
        nbt.putInt(KEY_GOAL_DELAY, doggoEntity.getGoalData(DoggoAction.PLAY_IN_SNOW).getDelayTicks());
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig iPluginConfig) {
        NbtCompound nbt = accessor.getServerData();

        if (nbt.contains(KEY_ACTION)) {
            tooltip.add(Text.literal("DoggoAction: " + nbt.getString(KEY_ACTION)));
        }

        if (nbt.contains(KEY_GOAL_DELAY)) {
            tooltip.add(Text.literal("Doggo Goal Delay: " + nbt.getInt(KEY_GOAL_DELAY)));
        }
    }

    @Override
    public Identifier getUid() {
        return identifier;
    }
}
