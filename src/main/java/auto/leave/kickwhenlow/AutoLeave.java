package auto.leave.kickwhenlow;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;


public class AutoLeave implements ModInitializer {
    public static boolean kicking=false;
    public static float kickat=5;
    @Override
    public void onInitialize() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->

        {
            dispatcher.register(
                    literal("startkicking").executes(context -> {
                        kicking=!kicking;
                        MinecraftClient.getInstance().player.sendMessage(Text.of("kicking will now be "+Boolean.toString(kicking)));
                        return 1;
                    })
            );
            dispatcher.register(
                    literal("kickat").then(argument("health",FloatArgumentType.floatArg(0)).executes(context -> {
                        kickat=FloatArgumentType.getFloat(context,"health");
                        MinecraftClient.getInstance().player.sendMessage(Text.of("Health to kick at is now "+Float.toString(kickat)));
                        return 1;
                    })
            ));
        });

    }
}
