package heyblack.visualsound.config;

import heyblack.visualsound.VisualSound;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = VisualSound.MOD_ID)
public class VisualSoundConfig implements ConfigData {
    @ConfigEntry.Category("general")
    public static boolean main_toggle = true;

    @ConfigEntry.Category("general")
    public static int time = 2000;

    @ConfigEntry.Category("general")
    public static int max_count = 128;

    @ConfigEntry.Category("text")
    @ConfigEntry.ColorPicker
    public static int text_color = 0xFFFFFF;

    @ConfigEntry.Category("text")
    @ConfigEntry.BoundedDiscrete(min = 32, max = 255)
    public static int text_alpha = 255;

    @ConfigEntry.Category("text")
    public static boolean use_bg = true;

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
