package auto.leave.autoleave.mixin;

import auto.leave.autoleave.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {
        @Inject(at = {@At("TAIL")}, method = {"Lnet/minecraft/client/network/ClientPlayerEntity;updateHealth(F)V"})
        public void updateHealth(float health, CallbackInfo callbackInfo) {
                final float kickat = ModConfig.HANDLER.instance().leaveAt;
                if(ModConfig.HANDLER.instance().doAutoLeave && health <= kickat){
                        //MinecraftClient.getInstance().world.networkHandler.getConnection().disconnect(Text.translatable("multiplayer.status.quitting"));
                        MinecraftClient.getInstance().getNetworkHandler().getConnection().disconnect(Text.translatable("autoleave.low_health.disconnect"));
                        ModConfig.HANDLER.instance().doAutoLeave = false;
                }
        }

}


