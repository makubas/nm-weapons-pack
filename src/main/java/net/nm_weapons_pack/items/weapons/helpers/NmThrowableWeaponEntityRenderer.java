package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NmThrowableWeaponEntityRenderer extends EntityRenderer<NmThrowableWeaponEntity> {
    private final NmThrowableWeaponEntityModel model;
    private final Identifier texture;

    protected NmThrowableWeaponEntityRenderer(EntityRendererFactory.Context ctx, Identifier texture, EntityModelLayer modelLayer) {
        super(ctx);
        this.model = new NmThrowableWeaponEntityModel(ctx.getPart(modelLayer));
        this.texture = texture;
    }

    @Override
    public Identifier getTexture(NmThrowableWeaponEntity entity) {
        return this.texture;
    }
}
