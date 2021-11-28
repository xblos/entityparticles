package net.xblos.entityparticles.particle;

import net.minecraft.util.math.Vec3d;

public class TextParticle extends Particle {

    private final String text;
    private final int color;
    private final int shrinkAge;
    private final float size;

    public TextParticle(Vec3d pos, int age, String text, int color, float size) {
        super(pos, age);
        this.text = text;
        this.color = color;
        this.size = size;
        shrinkAge = (int) ((float) age * .8f);
    }

    @Override
    public void tick() {
        ticks += 1;
        oldPos = pos;
        pos = pos.add(0, .05f * Math.min(1.0f, age * .1f), 0);
    }

    public float getScale() {
        return ticks > shrinkAge ? Math.min(1f, ((float) (age - ticks) * .7f)) : 1f;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public float getSize() {
        return size;
    }
}
