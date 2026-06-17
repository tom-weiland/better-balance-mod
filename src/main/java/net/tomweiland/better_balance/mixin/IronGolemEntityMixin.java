package net.tomweiland.better_balance.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.Level;

@Mixin(IronGolem.class)
public abstract class IronGolemEntityMixin extends AbstractGolem implements NeutralMob {

    protected IronGolemEntityMixin(EntityType<? extends AbstractGolem> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean isPushedByFluid() {
        // Prevent water from being used to push iron golems around in farms
        return false;
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (target instanceof Mob mob && mob.isPersistenceRequired()) {
            // Prevent iron golems from being lured to their death by persistent monsters
            return;
        }
        super.setTarget(target);
    }
}
