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

// TODO: fix rendering of text with background
public class VSTextRenderer {
    public static void renderContents(BlockPos blockPos, Map<String, Integer> contents, MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate, Camera camera) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (camera != null) {
            int i = 0;
            for (Map.Entry<String, Integer> entry : contents.entrySet()) {
                if (i > 5) {
                    return;
                }

                String content;

                if (entry.getValue() == 1) {
                    content = entry.getKey();
                } else {
                    content = entry.getKey() + "x" + entry.getValue();
                }
                Vec3d linePos = new Vec3d(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 0.5 + (i * 0.225),
                        blockPos.getZ() + 0.5
                );

                EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
                Vec3d vec3d = linePos.subtract(dispatcher.camera.getPos());

                matrixStack.push();
                matrixStack.translate(vec3d.x, vec3d.y, vec3d.z);

                Quaternion rotation = camera.getRotation().copy();
                matrixStack.multiply(rotation);

                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = matrixStack.peek().getModel();

                TextRenderer textRenderer = client.textRenderer;

                float offset = (float)(-textRenderer.getWidth(entry.getKey())) / 2.0F;

                int color = VisualSound.config.text_color;
                boolean useBg = VisualSound.config.use_bg;
                boolean useShadow = VisualSound.config.use_shadow;

                if (i == 5) {
                    textRenderer.draw(
                            "... +" + (contents.size() - 5),
                            offset,
                            0,
                            color,
                            useShadow,
                            matrix4f,
                            immediate,
                            true,
                            useBg ? 0x80000000 : 0,
                            15728640
                    );
                    matrixStack.pop();
                    i++;

                    return;
                }

                textRenderer.draw(
                        content,
                        offset,
                        0,
                        color,
                        useShadow,
                        matrix4f,
                        immediate,
                        true,
                        useBg ? 0x80000000 : 0,
                        15728640
                );
                matrixStack.pop();
                i++;
            }
        }
    }
}
