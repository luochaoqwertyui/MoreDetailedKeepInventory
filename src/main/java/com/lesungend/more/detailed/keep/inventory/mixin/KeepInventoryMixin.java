package com.lesungend.more.detailed.keep.inventory.mixin;

import com.lesungend.more.detailed.keep.inventory.KeepInventoryData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class KeepInventoryMixin {

	@Inject(method = "shouldDropLoot", at = @At("RETURN"), cancellable = true)
	private void onShouldDropLoot(ServerLevel level, CallbackInfoReturnable<Boolean> cir) {
		if (isKeepInventoryPlayer()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "shouldDropExperience", at = @At("RETURN"), cancellable = true)
	private void onShouldDropExperience(CallbackInfoReturnable<Boolean> cir) {
		if (isKeepInventoryPlayer()) {
			cir.setReturnValue(false);
		}
	}

	private boolean isKeepInventoryPlayer() {
		if ((Object) this instanceof ServerPlayer player) {
			return KeepInventoryData.isInSet(player.getUUID());
		}
		return false;
	}
}