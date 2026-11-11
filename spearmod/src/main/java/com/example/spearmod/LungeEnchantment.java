package com.example.spearmod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

/**
 * Lunge III only, spear only. A landed jab pushes the player forward
 * by 1.37 blocks per tick (27.48 blocks per second). Costs 1 durability
 * and 12 exhaustion (4 x level 3). Does not trigger while riding or
 * gliding, or with food below 7 (outside creative).
 */
public class LungeEnchantment extends Enchantment {

    public static final int LEVEL = 3;
    private static final double IMPULSE = 1.37;

    public LungeEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinLevel() {
        return LEVEL;
    }

    @Override
    public int getMaxLevel() {
        return LEVEL;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SpearItem;
    }

    public static void tryLunge(PlayerEntity player, ItemStack stack, Hand hand) {
        if (!(stack.getItem() instanceof SpearItem)) {
            return;
        }
        if (EnchantmentHelper.getLevel(SpearMod.LUNGE, stack) <= 0) {
            return;
        }
        if (player.hasVehicle() || player.isFallFlying()) {
            return;
        }
        if (!player.isCreative() && player.getHungerManager().getFoodLevel() < 7) {
            return;
        }

        Vec3d look = player.getRotationVector();
        Vec3d horizontal = new Vec3d(look.x, 0.0, look.z);
        if (horizontal.length() < 1.0E-4) {
            return;
        }
        Vec3d dir = horizontal.normalize();
        player.addVelocity(dir.x * IMPULSE, 0.0, dir.z * IMPULSE);
        player.velocityModified = true;

        player.addExhaustion(4.0F * LEVEL);
        stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
    }
}
