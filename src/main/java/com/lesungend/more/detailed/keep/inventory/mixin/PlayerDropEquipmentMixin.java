package com.lesungend.more.detailed.keep.inventory.mixin;

import com.lesungend.more.detailed.keep.inventory.KeepInventoryData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerDropEquipmentMixin {

	@Inject(method = "dropEquipment(Lnet/minecraft/server/level/ServerLevel;)V", at = @At("HEAD"), cancellable = true)
	private void onDropEquipment(ServerLevel level, CallbackInfo ci) {
		if ((Object) this instanceof ServerPlayer self) {
			if (KeepInventoryData.isInSet(self.getUUID())) {
				ci.cancel();
			}
		}
	}

	@Inject(method = "isAlwaysExperienceDropper", at = @At("RETURN"), cancellable = true)
	private void onIsAlwaysExperienceDropper(CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof ServerPlayer self) {
			if (KeepInventoryData.isInSet(self.getUUID())) {
				cir.setReturnValue(false);
			}
		}
	}
}