package net.nm_weapons_pack.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.utils.NmUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class BleedingParticle extends SpriteBillboardParticle {
    static final Identifier sprite = NmUtils.getNmId("particle/bleeding_particle");

    protected BleedingParticle(ClientWorld clientWorld, double d, double e, double f) {
        this(clientWorld, d, e, f, 0, 0, 0);
    }

    public BleedingParticle(ClientWorld clientWorld, double x, double y, double z, double v, double v1, double v2) {
        super(clientWorld, x, y, z, v, v1, v2);
        //this.setSprite((Sprite) MinecraftClient.getInstance().getSpriteAtlas(sprite));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            Random random = clientWorld.getRandom();

            BleedingParticle particle = new BleedingParticle(clientWorld, x, y, z, velocityX * random.nextDouble() * 0.2, (1 - random.nextDouble()) * 0.03, velocityZ * random.nextDouble() * 0.2);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
