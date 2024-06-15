package auto.leave.autoleave.client;

import auto.leave.autoleave.config.ModConfig;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class AutoLeaveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.HANDLER.load();
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("toggleautoleave")
                            .executes(context -> {
                        ModConfig.HANDLER.instance().doAutoLeave = !ModConfig.HANDLER.instance().doAutoLeave;
                        MinecraftClient.getInstance().player.sendMessage(Text.of("Auto Leave is now "+ ModConfig.HANDLER.instance().doAutoLeave));
                        return 1;
                    })
            );
            dispatcher.register(
                    literal("autoleaveat").
                            then(argument("health", IntegerArgumentType.integer(0)).
                                    executes(context -> {
                                ModConfig.HANDLER.instance().leaveAt = IntegerArgumentType.getInteger(context,"health");
                                MinecraftClient.getInstance().player.sendMessage(Text.of("Health to leave at is now "+ (ModConfig.HANDLER.instance().leaveAt)));

                                ModConfig.HANDLER.save();
                                return 1;
                            })
                    ));
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            ModConfig.HANDLER.instance().doAutoLeave = false;
            ModConfig.HANDLER.save();
        });
    }
}
