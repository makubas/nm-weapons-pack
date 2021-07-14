package net.nm_weapons_pack.dev;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.weapons.helpers.NmSwordWeapon;
import net.nm_weapons_pack.items.weapons.helpers.WeaponConfigSettings;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword(WeaponConfigSettings weaponConfigSettings) {
        super(weaponConfigSettings);
    }

    public TestSword(Identifier id) {
        super(id);
    }

    public static Identifier getId() {
        return id;
    }

    public static String getIdString() {
        return id.toString().replace("nm_weapons_pack:", "");
    }

    @Override
    public Text getName() {
        if (MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode().equals("en_us")) {
            return Text.of("Test Sword");
        } else {
            return super.getName();
        }
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        // sound playing sample
        // world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
            // TODO: Use it to make throwable weapons
            //EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, user);
            //enderPearlEntity.setItem(itemStack);
            //enderPearlEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            //world.spawnEntity(enderPearlEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        // Item decrementation sample
        // if (!user.getAbilities().creativeMode) {
        //    itemStack.decrement(1);
        //}

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
