package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.*;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.ThrowableWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.NmAttackMethod;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmStyle;
import net.nm_weapons_pack.utils.NmUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class NmThrowableWeapon extends TridentItem implements Vanishable {
    protected Rarity rarity;
    protected NmWeaponType weaponType;
    protected Identifier identifier;
    protected List<TranslatableText> tooltipsTexts = new ArrayList<>();
    protected NmWeaponMaterial material;
    protected NmAttackMethod attackMethod = NmAttackMethod.THROWABLE;
    protected int maxUseTime;
    protected EntityType<? extends NmThrowableWeaponEntity> entityType;

    public NmThrowableWeapon(String identifierString, NmWeaponType weaponType, EntityType<? extends NmThrowableWeaponEntity> entityType) {
        super(new FabricItemSettings()
                .rarity(NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getRarity())
                .group(NmItems.NM_WEAPONS_PACK_GROUP)
                .maxCount(NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getStackCount())
                .maxDamageIfAbsent((NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getMaterial().getDurability())));
        ThrowableWeaponConfigSettings throwableWeaponConfigSettings = NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString));
        this.identifier = NmUtils.getNmId(identifierString);
        this.material = throwableWeaponConfigSettings.getMaterial();
        this.rarity = throwableWeaponConfigSettings.getRarity();
        this.maxUseTime = throwableWeaponConfigSettings.getMaxUseTime();
        this.weaponType  = weaponType;
        this.entityType = entityType;
        initializeTooltip(this);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.maxUseTime;
    }

    public NmWeaponMaterial getMaterial() {
        return this.material;
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (TranslatableText text : tooltipsTexts) {
            tooltip.add(text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    protected Rarity getRarity() {
        return rarity;
    }

    public List<AbilityTooltip> getImplementedAbilities() {
        return new ArrayList<AbilityTooltip>();
    }

    public NmWeaponType getWeaponType() {
        return weaponType;
    }

    public Identifier getId() {
        return identifier;
    }

    public NmAttackMethod getAttackMethod() {
        return attackMethod;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 10) {
                int j = EnchantmentHelper.getRiptide(stack);
                if (j <= 0 || playerEntity.isTouchingWaterOrRain()) {
                    if (!world.isClient) {
                        stack.damage(1, playerEntity, (p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                        if (j == 0) {
                            NmThrowableWeaponEntity tridentEntity = createWeaponEntity(world, playerEntity, stack);
                            tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float)j * 0.5F, 1.0F);
                            if (playerEntity.getAbilities().creativeMode) {
                                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(tridentEntity);
                            world.playSoundFromEntity((PlayerEntity)null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerEntity.getAbilities().creativeMode) {
                                playerEntity.getInventory().removeOne(stack);
                            }
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    if (j > 0) {
                        float f = playerEntity.getYaw();
                        float g = playerEntity.getPitch();
                        float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float k = -MathHelper.sin(g * 0.017453292F);
                        float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float m = MathHelper.sqrt(h * h + k * k + l * l);
                        float n = 3.0F * ((1.0F + (float)j) / 4.0F);
                        h *= n / m;
                        k *= n / m;
                        l *= n / m;
                        playerEntity.addVelocity((double)h, (double)k, (double)l);
                        playerEntity.setRiptideTicks(20);
                        if (playerEntity.isOnGround()) {
                            float o = 1.1999999F;
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                        }

                        SoundEvent soundEvent3;
                        if (j >= 3) {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (j == 2) {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        world.playSoundFromEntity((PlayerEntity)null, playerEntity, soundEvent3, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    public @NotNull NmThrowableWeaponEntity createWeaponEntity(World world, LivingEntity user, ItemStack stack) {
        NmThrowableWeaponEntity NmThrowableWeaponEntity = this.entityType.create(world);
        NmThrowableWeaponEntity.setTridentAttributes(world, user, stack);
        NmThrowableWeaponEntity.setOwner(user);
        NmThrowableWeaponEntity.setTridentStack(stack);
        NmThrowableWeaponEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F, 1.0F);
        NmThrowableWeaponEntity.updatePosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
        return NmThrowableWeaponEntity;
    }

    // Tooltips functions

    private void addTooltip(TranslatableText text) {
        tooltipsTexts.add(text);
    }

    private void addAbilityTooltip(AbilityTooltip tooltip) {
        TranslatableText weaponAbilityTitle1 = (TranslatableText) new TranslatableText("Weapon Ability: " + tooltip.title() + " ").formatted(getRarity().formatting);
        TranslatableText weaponAbilityTitle2 = (TranslatableText) new TranslatableText(tooltip.type().toString()).formatted(Formatting.BOLD).formatted(getRarity().formatting);
        addTooltip((TranslatableText) weaponAbilityTitle1.append(weaponAbilityTitle2));
        addTooltip(tooltip.descriptionFormatted());
        if (tooltip.type() != AbilityType.PASSIVE) {
            addTooltip((TranslatableText) new TranslatableText("Cooldown: " + tooltip.cooldownSeconds() + " seconds").setStyle(NmStyle.COOLDOWN.getStyle()));
        }
        addEmptyTooltipLine();
    }

    protected void addRarityTooltip(Rarity rarity, NmWeaponType type) {
        addTooltip((TranslatableText) new TranslatableText(rarity.toString().toUpperCase() + " " + type.toString().toUpperCase().replace("_", " ")).setStyle(NmStyle.getStyle(rarity).withBold(true)));
    }

    protected void addEmptyTooltipLine() {
        addTooltip(new TranslatableText(""));
    }

    protected <T extends NmThrowableWeapon> void addAbilities(T t) {
        if (LeftClickAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((LeftClickAbility) t).getLeftClickAbilityTooltip());
        }
        if (RightClickAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((RightClickAbility) t).getRightClickAbilityTooltip());
        }
        if (PassiveAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((PassiveAbility) t).getPassiveAbilityTooltip());
        }
    }

    protected <T extends NmThrowableWeapon> void initializeTooltip(T t) {
        addAbilities(t);
        for (AbilityTooltip abilityTooltip : t.getImplementedAbilities()) {
            addAbilityTooltip(abilityTooltip);
        }
        addRarityTooltip(t.getRarity(), t.getWeaponType());
    }
}
