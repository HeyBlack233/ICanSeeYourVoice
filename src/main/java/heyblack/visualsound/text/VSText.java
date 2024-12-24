package heyblack.visualsound.text;

import heyblack.visualsound.config.VisualSoundConfig;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class VSText {
    private final BlockPos pos;
    private final String displayContent;

    private final long creationTime = Util.getMeasuringTimeMs();

    public VSText(WeightedSoundSet soundSet, BlockPos pos) {
        this.displayContent = soundSet.getSubtitle().getString();
        this.pos = pos;
    }

    public boolean isEnded(long currentTime) {
        return currentTime - creationTime > VisualSoundConfig.time;
    }

    public BlockPos getPos() {
        return pos;
    }

    public String getDisplayContent() {
        return Optional.ofNullable(displayContent).orElse(" ");
    }
}
