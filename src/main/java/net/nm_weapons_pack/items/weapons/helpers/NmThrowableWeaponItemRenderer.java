package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;

public class NmThrowableWeaponItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    private final Identifier id;
    private final Identifier weaponId;
    private final Identifier texture;
    private final EntityModelLayer modelLayer;
    private ItemRenderer itemRenderer;
    private NmThrowableWeaponEntityModel weaponModel;
    private BakedModel inventoryTridentModel;

    public NmThrowableWeaponItemRenderer(Identifier weaponId, Identifier texture, EntityModelLayer modelLayer) {
        this.id = new Identifier(weaponId.getNamespace(), weaponId.getPath() + "_renderer");
        this.weaponId = weaponId;
        this.texture = texture;
        this.modelLayer = modelLayer;
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode renderMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        assert this.weaponModel != null;
        if (renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED) {
            matrices.pop();
            matrices.push();
            itemRenderer.renderItem(stack, renderMode, false, matrices, vertexConsumers, light, overlay, this.inventoryTridentModel);
        } else {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.weaponModel.getLayer(this.texture), false, stack.hasGlint());
            this.weaponModel.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
        }
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public void reload(ResourceManager manager) {
        MinecraftClient mc = MinecraftClient.getInstance();
        this.itemRenderer = mc.getItemRenderer();
        this.weaponModel = new NmThrowableWeaponEntityModel(mc.getEntityModelLoader().getModelPart(this.modelLayer));
        this.inventoryTridentModel = mc.getBakedModelManager().getModel(new ModelIdentifier(this.weaponId + "_in_inventory", "inventory"));
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }
}
