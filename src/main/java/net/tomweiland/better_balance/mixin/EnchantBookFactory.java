package net.tomweiland.better_balance.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.village.TradeOffers;

@Mixin(TradeOffers.EnchantBookFactory.class)
public abstract class EnchantBookFactory implements TradeOffers.Factory {

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int reduceMaxEnchantLvl(Enchantment enchant) {
        return Math.max(enchant.getMinLevel(), enchant.getMaxLevel() - 2);
    }
}
