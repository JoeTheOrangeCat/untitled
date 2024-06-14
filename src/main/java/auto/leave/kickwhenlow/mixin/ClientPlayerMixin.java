package auto.leave.kickwhenlow.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static auto.leave.kickwhenlow.AutoLeave.kickat;
import static auto.leave.kickwhenlow.AutoLeave.kicking;


@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {
        @Inject(at = {@At("TAIL")}, method = {"Lnet/minecraft/client/network/ClientPlayerEntity;updateHealth(F)V"})
        public void updateHealth(float health,CallbackInfo callbackInfo)
        {
                if(kicking&&health<=kickat){
                        MinecraftClient.getInstance().world.disconnect();
                        kicking=false;
                }
        }

}


