package net.xblos.entityparticles.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.xblos.entityparticles.EntityParticles;

public class EntityTextParticle extends TextParticle {

    public EntityTextParticle(Entity target, int age, String text, int color, float size) {
        super(calculatePos(target), age, text, color, size);
    }

    private static Vec3d calculatePos(Entity target) {
        var offset = MinecraftClient.getInstance().gameRenderer.getCamera().getPos()
            .subtract(target.getPos())
            .normalize()
            .multiply(target.getWidth());

        return target.getPos().add(
            offset.x + (float) (EntityParticles.RAND.nextGaussian() * .04),
            offset.y + (target.getHeight() * .5f),
            offset.z + (float) (EntityParticles.RAND.nextGaussian() * .04)
        );
    }
}
