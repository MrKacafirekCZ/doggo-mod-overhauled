package net.lordmrk.dmo;

import net.minecraft.util.math.random.Random;

public class DoggoGoalData {

    private float chance;
    private final int delayRandomness;
    private int delayTicks;
    private int failedChanceAttempts;
    private final int minDelayTicks;
    private final Random random;

    public static float DEFAULT_CHANCE = 0.001f;

    public DoggoGoalData(Random random, int minDelayTicks, int delayRandomness) {
        this.chance = DEFAULT_CHANCE;
        this.delayRandomness = delayRandomness;
        this.delayTicks = 200;
        this.failedChanceAttempts = 0;
        this.random = random;
        this.minDelayTicks = minDelayTicks;
    }

    public boolean canStart() {
        if (this.delayTicks > 0) {
            return false;
        }

        return true;
    }

    public int getDelayTicks() {
        return delayTicks;
    }

    public boolean shouldStart() {
        if (this.random.nextFloat() < this.chance) {
            return true;
        }

        this.failedChanceAttempts++;

        if (this.failedChanceAttempts % 3 == 0) {
            this.chance += 0.001f;
        }

        return false;
    }

    public void start() {
        this.chance = DEFAULT_CHANCE;
        this.failedChanceAttempts = 0;
    }

    public void stop() {
        this.delayTicks = this.minDelayTicks + this.random.nextInt(this.delayRandomness) * 10;
    }

    public void tick() {
        if (this.delayTicks == 0) {
            return;
        }

        this.delayTicks--;
    }
}
