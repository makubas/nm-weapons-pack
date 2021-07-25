package net.nm_weapons_pack.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.utils.NmUtils;

public class NmParticles {
    public static DefaultParticleType BLEEDING_PARTICLE = new DefaultParticleType(false){};

    public static void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(BLEEDING_PARTICLE, BleedingParticle.Factory::new);
        registerParticle(NmUtils.getNmId("bleeding_particle"), BLEEDING_PARTICLE);
    }

    private static void registerParticle(Identifier id, DefaultParticleType particleType) {
        Registry.register(Registry.PARTICLE_TYPE, id, particleType);
    }
}
