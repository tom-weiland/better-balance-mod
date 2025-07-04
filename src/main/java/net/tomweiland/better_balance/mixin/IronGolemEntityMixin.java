package net.tomweiland.better_balance.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isPushedByFluids() {
        // Prevent water from being used to push iron golems around in farms
        return false;
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (target instanceof MobEntity mob && mob.isPersistent()) {
            // Prevent iron golems from being lured to their death by persistent monsters
            return;
        }
        super.setTarget(target);
    }
}
