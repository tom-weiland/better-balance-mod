package net.tomweiland.better_balance.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Either;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Unit;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "trySleep", at = @At("HEAD"), cancellable = true)
	private void onTrySleep(CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        // Prevent sleeping
        cir.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.NOT_SAFE));
	}
}
