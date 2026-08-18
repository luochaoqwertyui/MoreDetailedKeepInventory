package com.lesungend.more.detailed.keep.inventory.mixin;

import com.lesungend.more.detailed.keep.inventory.KeepInventoryData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Inject(method = "restoreFrom", at = @At("RETURN"))
	private void onRestoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
		ServerPlayer self = (ServerPlayer) (Object) this;
		if (KeepInventoryData.isInSet(self.getUUID())) {
			if (!alive) {
				self.getInventory().replaceWith(oldPlayer.getInventory());
				self.experienceLevel = oldPlayer.experienceLevel;
				self.totalExperience = oldPlayer.totalExperience;
				self.experienceProgress = oldPlayer.experienceProgress;
				self.setScore(oldPlayer.getScore());
			}
		}
	}
}