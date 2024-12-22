package heyblack.visualsound.config;

import heyblack.visualsound.VisualSound;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = VisualSound.MOD_ID)
public class VisualSoundConfig implements ConfigData {
    @ConfigEntry.Category("general")
    public boolean main_toggle = true;

    @ConfigEntry.Category("general")
    public int time = 2000;

    @ConfigEntry.Category("general")
    public int range = 64;

    @ConfigEntry.Category("general")
    public int max_count = 128;

    @ConfigEntry.Category("text")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int text_color = 0xFFFFFFFF;

    @ConfigEntry.Category("text")
    public boolean use_bg = true;

    @ConfigEntry.Category("text")
    public boolean use_shadow = false;

    @ConfigEntry.Category("other")
    public boolean debug = false;

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
