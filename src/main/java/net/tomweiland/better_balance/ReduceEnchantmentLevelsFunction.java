package net.tomweiland.better_balance;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * Loot function that re-rolls every enchantment level on the item uniformly within
 * [minLevel, max(minLevel, maxLevel - 2)]. Applied after {@code minecraft:enchant_randomly} in
 * the librarian book trades, it caps villager-traded enchantments two levels below their maximum
 */
public class ReduceEnchantmentLevelsFunction extends LootItemConditionalFunction {

    public static final MapCodec<ReduceEnchantmentLevelsFunction> MAP_CODEC =
        RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).apply(instance, ReduceEnchantmentLevelsFunction::new));

    protected ReduceEnchantmentLevelsFunction(List<LootItemCondition> predicates) {
        super(predicates);
    }

    @Override
    public MapCodec<? extends LootItemConditionalFunction> codec() {
        return MAP_CODEC;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        RandomSource random = context.getRandom();
        // Books store their enchantments under STORED_ENCHANTMENTS; gear uses ENCHANTMENTS.
        stack.update(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY, e -> reduce(e, random));
        stack.update(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY, e -> reduce(e, random));
        return stack;
    }

    private static ItemEnchantments reduce(ItemEnchantments enchantments, RandomSource random) {
        if (enchantments.isEmpty()) {
            return enchantments;
        }

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            int min = enchantment.value().getMinLevel();
            int reducedMax = Math.max(min, enchantment.value().getMaxLevel() - 2);
            mutable.set(enchantment, random.nextIntBetweenInclusive(min, reducedMax));
        }
        return mutable.toImmutable();
    }
}
