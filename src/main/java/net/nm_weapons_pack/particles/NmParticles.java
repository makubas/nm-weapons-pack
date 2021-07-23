package net.nm_weapons_pack.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.utils.NmUtils;

public class NmParticles {
    //public static final DefaultParticleType BLEEDING_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        //registerParticle(NmUtils.getNmId("bleeding_particle"), BLEEDING_PARTICLE);
    }

    private static void registerParticle(Identifier id, DefaultParticleType particle) {
        Registry.register(Registry.PARTICLE_TYPE, id, particle);
    }
}
