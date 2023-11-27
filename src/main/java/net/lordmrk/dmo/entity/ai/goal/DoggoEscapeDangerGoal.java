package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;

public class DoggoEscapeDangerGoal extends EscapeDangerGoal {

    public DoggoEscapeDangerGoal(DoggoEntity doggoEntity, double speed) {
        super(doggoEntity, speed);
    }

    protected boolean isInDanger() {
        return this.mob.shouldEscapePowderSnow() || this.mob.isOnFire();
    }
}
