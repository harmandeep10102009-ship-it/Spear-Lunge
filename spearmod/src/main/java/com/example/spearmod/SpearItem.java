package com.example.spearmod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.Vec3d;

/**
 * Netherite spear. Charge damage, applied on hit while the attacker is
 * closing on the target, is:
 *   1.2 (netherite multiplier, from the Minecraft Wiki) x closing speed in blocks/s
 * with a 4.6 blocks/s minimum.
 */
public class SpearItem extends SwordItem {

    private static final double CHARGE_MIN_SPEED = 4.6;
    private static final float NETHERITE_CHARGE_MULTIPLIER = 1.2F;

    public SpearItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.postHit(stack, target, attacker);

        if (target.world.isClient || !(attacker instanceof PlayerEntity)) {
            return result;
        }

        Vec3d toTarget = target.getPos().subtract(attacker.getPos());
        if (toTarget.length() < 1.0E-4) {
            return result;
        }
        Vec3d dir = toTarget.normalize();

        // Velocities are per tick; multiply by 20 for blocks per second.
        Vec3d relative = attacker.getVelocity().subtract(target.getVelocity());
        double closingSpeed = relative.dotProduct(dir) * 20.0;

        if (closingSpeed >= CHARGE_MIN_SPEED) {
            float damage = (float) (NETHERITE_CHARGE_MULTIPLIER * closingSpeed);
            target.damage(DamageSource.player((PlayerEntity) attacker), damage);
        }
        return result;
    }
}
