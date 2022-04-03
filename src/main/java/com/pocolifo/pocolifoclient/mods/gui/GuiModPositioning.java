package com.pocolifo.pocolifoclient.mods.gui;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import com.pocolifo.pocolifoclient.mods.gui.settings.GuiModSettings;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.impl.ButtonComponent;
import com.pocolifo.pocolifoclient.util.Fonts;
import com.pocolifo.pocolifoclient.util.Position;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiModPositioning extends GuiScreen {
    private static final float SCALE_BOX_SIZE = 6;
    private static final float HALF_SCALE_BOX_SIZE = SCALE_BOX_SIZE / 2;
    
    private int lastMx;
    private int lastMy;

    private RenderableMod draggingMod;
    private float lastDistanceToCenter;

    private ButtonComponent settingsButton;

    @Override
    public void drawScreen(int mx, int my, float partialTicks) {
        float changeX = mx - lastMx;
        float changeY = my - lastMy;

        for (RenderableMod mod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
            if (!mod.isEnabled()) continue;

            float x = mod.getPosition().getRenderX();
            float y = mod.getPosition().getRenderY();
            float width = mod.getScaledWidth();
            float height = mod.getScaledHeight();
            float maxX = x + width;
            float maxY = y + height;
            float modCenterX = x + mod.getScaledWidth() / 2;
            float modCenterY = y + mod.getScaledHeight() / 2;

            boolean hovering = mx > x && maxX > mx && my > y && maxY > my;
            boolean mouseDown = Mouse.isButtonDown(0);

            Position corner = getDraggingCorner(mx, my, mod);
            float distanceToCenterX = modCenterX - mx;
            float distanceToCenterY = modCenterY - my;
            float distanceToCenter = (float) Math.sqrt(distanceToCenterX * distanceToCenterX + distanceToCenterY * distanceToCenterY);


            if (mouseDown) {
                if (draggingMod == null) {
                    if (hovering) {
                        draggingMod = mod;
                    }
                } else if (mod == draggingMod) {
                    if (corner == null) {
                        draggingMod.getPosition().moveX(changeX);
                        draggingMod.getPosition().moveY(changeY);
                    } else {
                        if (this.lastMy != my || this.lastMx != mx) {
                            float distanceFromCorner = getDistanceFromCenterOfNearestCorner(mx, my, mod);
                            float scaleChange = distanceFromCorner / mod.getScaledWidth();

                            if (distanceToCenter > lastDistanceToCenter) {
                                // bigger
                                draggingMod.getPosition().changeScale(scaleChange);
                            } else {
                                // smaller
                                draggingMod.getPosition().changeScale(-scaleChange);
                            }
                        }
                    }
                }
            } else {
                if (draggingMod == mod) {
                    draggingMod = null;
                    lastDistanceToCenter = 0;
                }
            }

            if (draggingMod == mod) {
                lastDistanceToCenter = distanceToCenter;
            }

            // draw scale corners
            if (hovering) {
                // top left
                Geometry.drawFullRectangle(x, y, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

                // top right
                Geometry.drawFullRectangle(x + width - SCALE_BOX_SIZE, y, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

                // bottom left
                Geometry.drawFullRectangle(x, y + height - SCALE_BOX_SIZE, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

                // bottom right
                Geometry.drawFullRectangle(x + width - SCALE_BOX_SIZE, y + height - SCALE_BOX_SIZE, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);
            }

            // set highlight color
            ClientColor color = hovering ? Colors.POCOLIFO_PURPLE.color : Colors.POCOLIFO_BLUE.color;
            ClientColor alphaColor = new ClientColor(color.red, color.green, color.blue, 0.25f);

            // draw mod outline
            Geometry.drawFullRectangle(mod.getPosition().getRenderX(), mod.getPosition().getRenderY(), mod.getScaledWidth(), mod.getScaledHeight(), alphaColor);
            Geometry.drawLinedRectangle(mod.getPosition().getRenderX(), mod.getPosition().getRenderY(), mod.getScaledWidth(), mod.getScaledHeight(), 0.5f, color);
            Fonts.regular.write(mod.getModName(), mod.getPosition().getRenderX(), mod.getPosition().getRenderY() + mod.getScaledHeight(), Colors.WHITE.color);
        }

        lastMx = mx;
        lastMy = my;

        this.settingsButton.render();

        if (this.settingsButton.isHovering()) {
            this.settingsButton.setColor(Colors.BLACK_TRANSPARENT.color.coverBy(0.2f));
        } else {
            this.settingsButton.setColor(Colors.BLACK_TRANSPARENT.color);
        }

        if (this.settingsButton.isPressed()) {
            this.mc.displayGuiScreen(new GuiModSettings());
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        }
    }

    private float getDistanceFromCenterOfNearestCorner(float mx, float my, RenderableMod mod) {
        float width = mod.getScaledWidth();
        float height = mod.getScaledHeight();
        float x = mod.getPosition().getRenderX();
        float y = mod.getPosition().getRenderY();
        float modCenterX = x + mod.getScaledWidth() / 2;
        float modCenterY = y + mod.getScaledHeight() / 2;

        float cornerX;
        float cornerY;

        if (mx > modCenterX) {
            // right side
            if (my > modCenterY) {
                // bottom right
                cornerX = x + width - HALF_SCALE_BOX_SIZE;
                cornerY = y + height - HALF_SCALE_BOX_SIZE;
            } else {
                // top right
                cornerX = x + width - HALF_SCALE_BOX_SIZE;
                cornerY = y + HALF_SCALE_BOX_SIZE;
            }
        } else {
            // left side
            if (my > modCenterY) {
                // bottom left
                cornerX = x + HALF_SCALE_BOX_SIZE;
                cornerY = y + height - HALF_SCALE_BOX_SIZE;
            } else {
                // top left
                cornerX = x + HALF_SCALE_BOX_SIZE;
                cornerY = y + HALF_SCALE_BOX_SIZE;
            }
        }

        float mouseCornerDistanceX = mx - cornerX;
        float mouseCornerDistanceY = my - cornerY;
        return (float) Math.sqrt(mouseCornerDistanceX * mouseCornerDistanceX + mouseCornerDistanceY * mouseCornerDistanceY);
    }

    private Position getDraggingCorner(float mx, float my, RenderableMod mod) {
        float width = mod.getScaledWidth();
        float height = mod.getScaledHeight();
        float x = mod.getPosition().getRenderX();
        float y = mod.getPosition().getRenderY();

        if (mx > x && x + SCALE_BOX_SIZE > mx && my > y && y + SCALE_BOX_SIZE > my) {
            return Position.TOP_LEFT;
        }

        // top right
        if (mx > x + width - SCALE_BOX_SIZE && x + width > mx && my > y && y + SCALE_BOX_SIZE > my) {
            return Position.TOP_RIGHT;
        }

        // bottom left
        if (mx > x && x + SCALE_BOX_SIZE > mx && my > y + height - SCALE_BOX_SIZE && y + height > my) {
            return Position.BOTTOM_LEFT;
        }

        // bottom right
        if (mx > x + width - SCALE_BOX_SIZE && x + width > mx && my > y + height - SCALE_BOX_SIZE && y + height > my) {
            return Position.BOTTOM_RIGHT;
        }

        return null;
    }

    @Override
    public void initGui() {
        int settingsWidth = 150;
        int settingsHeight = 30;

        this.settingsButton = new ButtonComponent("Mod Settings", this.width / 2f - settingsWidth / 2f, this.height / 2f - settingsHeight / 2f, settingsWidth, settingsHeight, Colors.BLACK_TRANSPARENT.color, Colors.WHITE.color);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
