package net.lordmrk.dmo.entity.ai.goal;

import net.lordmrk.dmo.entity.DoggoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.passive.LlamaEntity;

public class DoggoAvoidLlamaGoal<T extends LivingEntity> extends FleeEntityGoal<T> {

    private final DoggoEntity doggoEntity;

    public DoggoAvoidLlamaGoal(DoggoEntity doggoEntity, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(doggoEntity, fleeFromType, distance, slowSpeed, fastSpeed);
        this.doggoEntity = doggoEntity;
    }

    public boolean canStart() {
        if (super.canStart() && this.targetEntity instanceof LlamaEntity) {
            return !this.doggoEntity.isTamed() && this.isScaredOf((LlamaEntity)this.targetEntity);
        } else {
            return false;
        }
    }

    private boolean isScaredOf(LlamaEntity llama) {
        return llama.getStrength() >= doggoEntity.getRandom().nextInt(5);
    }

    public void start() {
        this.doggoEntity.setTarget(null);
        super.start();
    }

    public void tick() {
        this.doggoEntity.setTarget(null);
        super.tick();
    }
}
