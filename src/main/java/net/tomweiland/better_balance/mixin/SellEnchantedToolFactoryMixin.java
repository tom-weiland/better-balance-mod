package net.tomweiland.better_balance.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;

@Mixin(TradeOffers.SellEnchantedToolFactory.class)
public abstract class SellEnchantedToolFactoryMixin implements TradeOffers.Factory {
    
    @ModifyVariable(method = "Lnet/minecraft/village/TradeOffers$SellEnchantedToolFactory;<init>(Lnet/minecraft/item/Item;IIIF)V", at = @At("HEAD"))
    private static Item replaceDiamondGear(Item item) {
        // Replace diamond gear trades with iron & chainmail
        if (item == Items.DIAMOND_BOOTS)
            return Items.CHAINMAIL_BOOTS;

        if (item == Items.DIAMOND_LEGGINGS)
            return Items.CHAINMAIL_LEGGINGS;

        if (item == Items.DIAMOND_CHESTPLATE)
            return Items.CHAINMAIL_CHESTPLATE;

        if (item == Items.DIAMOND_HELMET)
            return Items.CHAINMAIL_HELMET;

        if (item == Items.DIAMOND_PICKAXE)
            return Items.IRON_PICKAXE;

        if (item == Items.DIAMOND_AXE)
            return Items.IRON_AXE;

        if (item == Items.DIAMOND_SHOVEL)
            return Items.IRON_SHOVEL;

        if (item == Items.DIAMOND_SWORD)
            return Items.IRON_SWORD;

        return item;
    }
}
