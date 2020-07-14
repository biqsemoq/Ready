package xyz.semoteo.ready.font;

import java.awt.*;
import java.io.*;

public class FontManager
{
    public OTFFontRenderer semosaksofon_t;
    
    public void setup() {
        this.semosaksofon_t = new OTFFontRenderer(new Font("Liberation", 0, 18));
    }
    
    private OTFFontRenderer createFont(final String name, final int type, final int size) {
        try {
            final InputStream is = this.getClass().getResourceAsStream("/assets/minecraft/ready/" + name);
            final Font font = Font.createFont(type, is).deriveFont(type, (float)size);
            return new OTFFontRenderer(font);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
