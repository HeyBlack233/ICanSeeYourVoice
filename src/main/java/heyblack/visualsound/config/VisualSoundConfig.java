package heyblack.visualsound.config;

import heyblack.visualsound.VisualSound;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = VisualSound.MOD_ID)
public class VisualSoundConfig implements ConfigData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    @ConfigEntry.Category("style")
    @ConfigEntry.Gui.TransitiveObject
    public Style style = new Style();

    public static class General {
        public boolean main_toggle = true;
        public int time = 2000;
        public int max_count = 128;
    }

    public static class Style {
        @ConfigEntry.ColorPicker
        public int text_color = 0xFFFFFF;

        @ConfigEntry.BoundedDiscrete(min = 32, max = 255)
        public int text_alpha = 255;

        public boolean use_bg = true;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }
}
