package heyblack.visualsound;

import heyblack.visualsound.config.VisualSoundConfig;
import heyblack.visualsound.text.VSTextManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VisualSound implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "visualsound";
    public static final String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();

    public static VSTextManager textManager;

    public static VisualSoundConfig config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(VisualSoundConfig.class, GsonConfigSerializer::new);

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            textManager = VSTextManager.getInstance();
            config = AutoConfig.getConfigHolder(VisualSoundConfig.class).getConfig();
        });
    }
}
