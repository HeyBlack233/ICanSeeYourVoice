package heyblack.visualsound.util;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import heyblack.visualsound.config.VisualSoundConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class VisualSoundModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(VisualSoundConfig.class, parent).get();
    }
}
