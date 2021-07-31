package net.nm_weapons_pack.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.utils.NmUtils;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class BleedingParticle extends SpriteBillboardParticle {
    static final Identifier sprite = NmUtils.getNmId("particle/bleeding_particle");

    protected BleedingParticle(ClientWorld clientWorld, double d, double e, double f) {
        this(clientWorld, d, e, f, 0, 0, 0);
    }

    public BleedingParticle(ClientWorld clientWorld, double x, double y, double z, double v, double v1, double v2) {
        super(clientWorld, x, y, z, v, v1, v2);
        Random random = clientWorld.getRandom();
        this.setColor(0.56F + random.nextFloat() * 0.1f, 0.12F + random.nextFloat() * 0.1f, 0.12F + random.nextFloat() * 0.1f);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.collidesWithWorld = true;
        this.scale *= clientWorld.random.nextFloat() * 0.4F;
        this.maxAge = 100;
        this.gravityStrength = 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            this.collidesWithWorld = false;
            this.y += 0.02;
            this.setVelocity(0, 0, 0);
            this.gravityStrength = 0;
        }
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

            BleedingParticle particle = new BleedingParticle(clientWorld, x, y, z, velocityX * random.nextDouble() * 0.2, 1, velocityZ * random.nextDouble() * 0.2);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
