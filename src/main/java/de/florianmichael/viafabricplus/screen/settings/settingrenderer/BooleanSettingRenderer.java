package de.florianmichael.viafabricplus.screen.settings.settingrenderer;

import de.florianmichael.viafabricplus.screen.settings.AbstractSettingRenderer;
import de.florianmichael.viafabricplus.settings.impl.BooleanSetting;
import de.florianmichael.viafabricplus.util.ScreenUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;

public class BooleanSettingRenderer extends AbstractSettingRenderer {
    private final BooleanSetting value;

    public BooleanSettingRenderer(BooleanSetting value) {
        this.value = value;
    }

    @Override
    public Text getNarration() {
        return Text.literal(this.value.getName());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.value.setValue(!this.value.getValue());
        ScreenUtil.playClickSound();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        matrices.push();
        matrices.translate(x, y, 0);
        DrawableHelper.fill(matrices, 0, 0, entryWidth - 4 /* int i = this.left + (this.width - entryWidth) / 2; int j = this.left + (this.width + entryWidth) / 2; */, entryHeight, Integer.MIN_VALUE);
        final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        final String text = this.value.getValue() ? "On" : "Off";

        textRenderer.drawWithShadow(matrices, Formatting.GRAY + this.value.getName(), 3, entryHeight / 2F - textRenderer.fontHeight / 2F, -1);
        textRenderer.drawWithShadow(matrices, text, entryWidth - textRenderer.getWidth(text) - 3 - 3, entryHeight / 2F - textRenderer.fontHeight / 2F, this.value.getValue() ? Color.GREEN.getRGB() : Color.RED.getRGB());

        matrices.pop();
    }
}