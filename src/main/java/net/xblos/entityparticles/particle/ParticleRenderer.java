package net.xblos.entityparticles.particle;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.math.MathHelper.lerp;

public class ParticleRenderer {

    private final List<Particle> particles = new ArrayList<>();

    public void add(EntityTextParticle particle) {
        particles.add(particle);
    }

    public void tick() {
        if (particles.isEmpty()) return;
        particles.forEach(Particle::tick);
        particles.removeIf(Particle::isDead);
    }

    public void draw(WorldRenderContext ctx, MinecraftClient client) {
        if (particles.isEmpty()) return;
        var tickDelta = ctx.tickDelta();
        var camera = ctx.camera();
        var camPos = camera.getPos();
        var matrixStack = ctx.matrixStack();

        for (var particle : particles) {
            if (particle instanceof EntityTextParticle entityTextParticle)
                drawEntityTextParticle(entityTextParticle, tickDelta, camPos, matrixStack, client);
        }
    }

    private void drawEntityTextParticle(EntityTextParticle particle, float tickDelta, Vec3d camPos, MatrixStack matrixStack, MinecraftClient client) {
        var x = lerp(tickDelta, particle.oldPos.x, particle.pos.x) - camPos.x;
        var y = lerp(tickDelta, particle.oldPos.y, particle.pos.y) - camPos.y;
        var z = lerp(tickDelta, particle.oldPos.z, particle.pos.z) - camPos.z;

        matrixStack.push();
        matrixStack.translate(x, y, z);
        matrixStack.multiply(client.getEntityRenderDispatcher().getRotation());

        var size = particle.getSize();
        var textScale = particle.getScale();
        matrixStack.scale(-size, -size, size);
        matrixStack.scale(textScale, textScale, textScale);

        var textWidth = client.textRenderer.getWidth(particle.getText());
        var text = new LiteralText(particle.getText()).setStyle(Style.EMPTY.withBold(true));

        client.textRenderer.draw(matrixStack, text, 5 - textWidth, 0, particle.getColor());
        matrixStack.pop();
    }

    public void draw(WorldRenderContext ctx) {
        draw(ctx, MinecraftClient.getInstance());
    }
}
