package heyblack.visualsound.text;

import heyblack.visualsound.config.VisualSoundConfig;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class VSText {
    private final BlockPos pos;
    private final Text displayContent;

    private final long creationTime = Util.getMeasuringTimeMs();

    public VSText(WeightedSoundSet soundSet, BlockPos pos) {
        this.displayContent = soundSet.getSubtitle();
        this.pos = pos;
    }

    public boolean isEnded(long currentTime) {
        return currentTime - creationTime > VisualSoundConfig.time;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Text getDisplayContent() {
        return displayContent;
    }
}
