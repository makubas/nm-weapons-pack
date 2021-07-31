package net.nm_weapons_pack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeapon;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeaponEntity;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeaponEntityRenderer;

public class NmWeaponsPackClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerTridents();
    }

    private static void registerTridents() {
        for (NmThrowableWeapon item : NmItems.ALL_TRIDENTS) {
            Identifier weaponId = Registry.ITEM.getId(item);
            Identifier texture = new Identifier(weaponId.getNamespace(), "textures/entity/" + weaponId.getPath() + ".png");

            EntityModelLayer modelLayer = EntityModelLayers.TRIDENT;
            ImpaledTridentItemRenderer tridentItemRenderer = new ImpaledTridentItemRenderer(tridentId, texture, modelLayer);

            FabricModelPredicateProviderRegistry.register(item, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(weaponId + "_in_inventory", "inventory")));
        }
    }
}
