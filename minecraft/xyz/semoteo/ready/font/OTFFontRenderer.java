package xyz.semoteo.ready.font;


import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;

/*
    Made by YARRAKTEO at 29.04.2019
 */

public class OTFFontRenderer {



    private int style;
    private int size;
    private UnicodeFont font;
    private final int[] colorCodes = new int[32];


    public OTFFontRenderer(Font _font) {
        this.font = new UnicodeFont(_font);
        this.size = _font.getSize();
        this.style = _font.getStyle();


        this.font.addAsciiGlyphs();
        this.font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        try
        {
            this.font.loadGlyphs();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < 32; i++)
        {
            int shadow = (i >> 3 & 0x1) * 85;
            int red = (i >> 2 & 0x1) * 170 + shadow;
            int green = (i >> 1 & 0x1) * 170 + shadow;
            int blue = (i >> 0 & 0x1) * 170 + shadow;
            if (i == 6) {
                red += 85;
            }
            if (i >= 16)
            {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }

    public void renderString(String text, float x, float y, Color color) {
        this.renderString(text,x,y,color.getRGB());
    }

    public int renderString(String text, float x, float y, int color) {
        if(text == "" || text.length() == 0) return (int)x;


        x *= 2.0F;
        y *= 2.0F;
        float originalX = x;

        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        boolean blend = GL11.glIsEnabled(3042);
        boolean lighting = GL11.glIsEnabled(2896);
        boolean texture = GL11.glIsEnabled(3553);
        if (!blend) {
            GL11.glEnable(3042);
        }
        if (lighting) {
            GL11.glDisable(2896);
        }
        if (texture) {
            GL11.glDisable(3553);
        }
        int currentColor = color;
        char[] characters = text.toCharArray();

        int index = 0;
        char[] arrayOfChar1;
        int j = (arrayOfChar1 = characters).length;
        for (int i = 0; i < j; i++)
        {
            char c = arrayOfChar1[i];
            if (c == '\r') {
                x = originalX;
            }
            if (c == '\n') {
                y += getStringHeight(Character.toString(c)) * 2.0F;
            }
            if ((c != 65533) && ((index == 0) || (index == characters.length - 1) || (characters[(index - 1)] != 65533)))
            {
                this.font.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
                x += getStringWidth(Character.toString(c)) * 2.0F;
            }
            else if (c == ' ')
            {
                x += this.font.getSpaceWidth();
            }
            else if ((c == 65533) && (index != characters.length - 1))
            {
                int codeIndex = "0123456789abcdefg".indexOf(text.toLowerCase().charAt(index + 1));
                if (codeIndex < 0) {
                    continue;
                }
                int col = this.colorCodes[codeIndex];
                currentColor = col;
            }
            index++;
        }
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        if (texture) {
            GL11.glEnable(3553);
        }
        if (lighting) {
            GL11.glEnable(2896);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
        return (int)x;
    }

    public int drawStringWithShadow(String text, float x, float y, int color)
    {
        renderString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0);
        return renderString(text, x, y, color);
    }

    public void renderCenteredString(String text, float x, float y, int color)
    {
        renderString(text, x - getStringWidth(text) / 2, y, color);
    }

    public void renderCenteredStringWithShadow(String text, float x, float y, int color)
    {
        renderCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, color);
        renderCenteredString(text, x, y, color);
    }

    public float getStringWidth(String s)
    {
        float width = 0.0F;

        String str = StringUtils.stripControlCodes(s);
        char[] arrayOfChar;
        int j = (arrayOfChar = str.toCharArray()).length;
        for (int i = 0; i < j; i++)
        {
            char c = arrayOfChar[i];
            width += this.font.getWidth(Character.toString(c)) + 0;
        }
        return width / 2.0F;
    }

    public float getCharWidth(char c)
    {
        return this.font.getWidth(String.valueOf(c));
    }

    public float getStringHeight(String s)
    {
        return this.font.getHeight(s) / 2.0F;
    }

    public UnicodeFont getFont()
    {
        return this.font;
    }

    public int getSize() {
        return size;
    }

    public int getStyle() {
        return style;
    }
}
