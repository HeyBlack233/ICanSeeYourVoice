package heyblack.visualsound.text;

import heyblack.visualsound.VisualSound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class VSTextRenderer {
    public static void renderContentsInWorld(BlockPos blockPos, Map<String, Integer> contents, MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate, EntityRenderDispatcher dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = dispatcher.camera;

        if (camera != null) {
            float offset1 = 0;
            int lines = 0;
            for (Map.Entry<String, Integer> entry : contents.entrySet()) {
                if (lines > 5) {
                    return;
                }

                TextRenderer textRenderer = client.textRenderer;

                if (lines == 4) {
                    offset1 = (float)(-textRenderer.getWidth(entry.getKey())) / 2.0F;
                }

                String content;

                if (entry.getValue() == 1) {
                    content = entry.getKey();
                } else {
                    content = entry.getKey() + "x" + entry.getValue();
                }
                Vec3d linePos = new Vec3d(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 0.5 + (lines * 0.225),
                        blockPos.getZ() + 0.5
                );

                Vec3d vec3d = linePos.subtract(dispatcher.camera.getPos());

                matrixStack.push();
                matrixStack.translate(vec3d.x, vec3d.y, vec3d.z);

                Quaternion rotation = dispatcher.camera.getRotation().copy();
                matrixStack.multiply(rotation);

                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = matrixStack.peek().getModel();

                float offset = (float)(-textRenderer.getWidth(entry.getKey())) / 2.0F;

                int alpha = VisualSound.getConfig().style.text_alpha;
                int color = getARGB(alpha, VisualSound.getConfig().style.text_color);
                boolean useBg = VisualSound.getConfig().style.use_bg;

                if (lines == 5) {
                    if (useBg) {
                        textRenderer.draw("... +" + (contents.size() - 5), offset1, 0, getARGB(32, VisualSound.getConfig().style.text_color), false, matrix4f, immediate, true, 0x50000000, 15728640);
                        textRenderer.draw("... +" + (contents.size() - 5), offset1, 0, getARGB(getInputAlphaWithTargetBlendedValue(32, alpha), VisualSound.getConfig().style.text_color), false, matrix4f, immediate, false, 0, 15728640);
                    } else {
                        textRenderer.draw(
                                "... +" + (contents.size() - 5),
                                offset1,
                                0,
                                color,
                                true,
                                matrix4f,
                                immediate,
                                true,
                                0,
                                15728640
                        );

                    }
                } else {
                    if (useBg) {
                        textRenderer.draw(content, offset, 0, getARGB(32, VisualSound.getConfig().style.text_color), false, matrix4f, immediate, true, 0x50000000, 15728640);
                        textRenderer.draw(content, offset, 0, getARGB(getInputAlphaWithTargetBlendedValue(32, alpha), VisualSound.getConfig().style.text_color), false, matrix4f, immediate, false, 0, 15728640);
                    } else {
                        textRenderer.draw(
                                content,
                                offset,
                                0,
                                color,
                                true,
                                matrix4f,
                                immediate,
                                true,
                                0,
                                15728640
                        );
                    }
                }
                matrixStack.pop();
                lines++;
            }
        }
    }

    private static int getARGB(int alpha, int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    @SuppressWarnings("SameParameterValue")
    private static int getInputAlphaWithTargetBlendedValue(int inputFirst, int target) {
        float fI = inputFirst / 255.0f;
        float fT = target / 255.0f;

        float output = (fT - fI) / (1 - fI);

        return Math.round(output * 255);
    }
}
