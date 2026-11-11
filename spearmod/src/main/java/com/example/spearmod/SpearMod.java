package com.example.spearmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpearMod implements ModInitializer {

    public static final String MOD_ID = "spearmod";

    // Netherite spear: material damage 4 + 1 = 5 jab damage.
    public static final Item NETHERITE_SPEAR = new SpearItem(
            ToolMaterials.NETHERITE, 1, -2.8F,
            new Item.Settings().group(ItemGroup.COMBAT).fireproof());

    public static final Enchantment LUNGE = new LungeEnchantment();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "netherite_spear"), NETHERITE_SPEAR);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "lunge"), LUNGE);

        // Lunge fires before the hit resolves, so a charge right after a lunge uses the new speed.
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient) {
                LungeEnchantment.tryLunge(player, player.getStackInHand(hand), hand);
            }
            return ActionResult.PASS;
        });
    }
}
