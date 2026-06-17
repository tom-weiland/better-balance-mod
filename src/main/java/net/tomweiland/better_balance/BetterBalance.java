package net.tomweiland.better_balance;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterBalance implements ModInitializer {
	public static final String MOD_ID = "better_balance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Register the custom loot function used by the rebalanced librarian book trades.
		Registry.register(
			BuiltInRegistries.LOOT_FUNCTION_TYPE,
			Identifier.fromNamespaceAndPath(MOD_ID, "reduce_enchantment_levels"),
			ReduceEnchantmentLevelsFunction.MAP_CODEC);
	}
}