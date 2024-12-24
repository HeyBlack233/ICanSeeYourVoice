package heyblack.visualsound.text;

import heyblack.visualsound.config.VisualSoundConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class VSTextManager implements SoundInstanceListener {
    private static final VSTextManager INSTANCE = new VSTextManager();

    public static VSTextManager getInstance() {
        return INSTANCE;
    }

    public VSTextManager() {
        MinecraftClient.getInstance().getSoundManager().registerListener(this);
    }

    private final List<VSText> VSTexts = new ArrayList<>();

    private final Map<BlockPos, Map<String, Integer>> posContentsMap = new LinkedHashMap<>();

    public void addText(SoundInstance sound, WeightedSoundSet soundSet) {
        BlockPos pos = BlockPos.ofFloored(sound.getX(), sound.getY(), sound.getZ());

        if (VSTexts.size() < VisualSoundConfig.max_count) {
            VSText text = new VSText(soundSet, pos);
            VSTexts.add(text);

            Map<String, Integer> contents = Optional.ofNullable(posContentsMap.get(pos)).orElse(new LinkedHashMap<>());
            String content = text.getDisplayContent();

            if (contents.containsKey(content)) {
                int i = contents.get(content);
                i++;
                contents.put(content, i);
            } else {
                contents.put(content, 1);
            }
            posContentsMap.put(pos, contents);
        }
    }

    public void tick(MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate) {
        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        Set<BlockPos> posSet = new HashSet<>();

        // handle existing texts
        Iterator<VSText> iterator = VSTexts.iterator();
        while (iterator.hasNext()) {
            VSText text = iterator.next();

            BlockPos pos = text.getPos();
            String content = text.getDisplayContent();

            if (text.isEnded(Util.getMeasuringTimeMs())) {
                Map<String, Integer> contents = posContentsMap.get(pos);
                int i = contents.get(content);
                i--;

                if (i == 0) {
                    contents.remove(content);
                } else {
                    contents.put(content, i);
                }
                posContentsMap.put(pos, contents);

                iterator.remove();
            }

            posSet.add(pos);
        }

        // render contents in every pos
        Iterator<BlockPos> iterator1 = posSet.iterator();
        while (iterator1.hasNext()) {
            BlockPos pos = iterator1.next();

            Map<String, Integer> contents = posContentsMap.get(pos);

            VSTextRenderer.renderContentsInWorld(pos, contents, matrixStack, immediate, dispatcher);
        }
    }

    @Override
    public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
        if (VisualSoundConfig.main_toggle) {
            if (soundSet.getSubtitle() != null) {
                this.addText(sound, soundSet);
            }
        }
    }
}
