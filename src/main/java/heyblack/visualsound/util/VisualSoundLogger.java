package heyblack.visualsound.util;

import heyblack.visualsound.VisualSound;
import org.apache.logging.log4j.Logger;

public class VisualSoundLogger {
    private static final Logger LOGGER = VisualSound.LOGGER;

    public static void info(String s) {
        LOGGER.info(s);
    }

    public static void warn(String s) {
        LOGGER.warn(s);
    }

    public static void error(String s) {
        LOGGER.error(s);
    }
}
