package net.darkhax.maxhealthfix.mixin;

import net.darkhax.maxhealthfix.IHealthFixable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class MixinPlayerList {

    @Inject(
            method = "respawn(Lnet/minecraft/server/level/ServerPlayer;Z)Lnet/minecraft/server/level/ServerPlayer;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V")
    )
    private void onPlayerRespawn(ServerPlayer playerIn, boolean conqueredEnd, CallbackInfoReturnable<ServerPlayer> cir) {
        // Получаем мир игрока
        ServerLevel spawnWorld = (ServerLevel) playerIn.getCommandSenderWorld();

        // Логика фикса здоровья
        if (playerIn instanceof IHealthFixable fixable) {
            fixable.maxhealthfix$setRestorePoint(playerIn.getMaxHealth());
        }
    }
}
