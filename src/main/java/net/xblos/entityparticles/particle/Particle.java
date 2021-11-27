package net.xblos.entityparticles.particle;

import net.minecraft.util.math.Vec3d;

public abstract class Particle {

    protected Vec3d oldPos;
    protected Vec3d pos;
    protected int ticks;
    protected int age;

    public Particle(Vec3d pos, int age) {
        this.pos = pos;
        this.oldPos = pos;
        this.ticks = 0;
        this.age = age;
    }

    public abstract void tick();

    public boolean isDead() {
        return ticks >= age;
    }

    public Vec3d getOldPos() {
        return oldPos;
    }

    public Vec3d getPos() {
        return pos;
    }
}
