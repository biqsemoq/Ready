package xyz.semoteo.ready;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;

public class Tabgui {
  private int semoteolarlarlarlarllarlarlarl = 0;
  
  private int selectedCategory;
  
  private int selectetModuleindex = 0;
  
  private boolean isSemoteoOrospuCocugu;
  
  private boolean nolduOrospuCocugu;
  
  int hoverColor;
  
  public Tabgui() {
    this.hoverColor = -10567907;
  }
  
  public void render() {
    int curIndex = 0;
    int y = 15;
    int height = (Category.values()).length * 13 + y;
    drawBorderedRect(2.0F, y, 70.0F, height, 1.0F, -1442840576, -1442840576);
    byte b;
    int i;
    Category[] arrayOfCategory;
    for (i = (arrayOfCategory = Category.values()).length, b = 0; b < i; ) {
      Category category = arrayOfCategory[b];
      if (curIndex == this.selectedCategory) {
        Gui.drawRect(2, y, 70, y + 10, this.hoverColor);
        Ready.fm.semosaksofon_t.drawStringWithShadow(category.name(), 5.0F, y, -1);
      } else {
        Ready.fm.semosaksofon_t.drawStringWithShadow(category.name(), 3.0F, y, 11184810);
      } 
      if (curIndex == this.selectedCategory && this.isSemoteoOrospuCocugu) {
        List<Mod> mods = getModsInCategory(category);
        int height2 = mods.size() * 10 + y;
        drawBorderedRect(75.0F, y, 160.0F, height2, 1.0F, -1442840576, -1442840576);
        int yarrakizm = 0;
        int pantolonKiranModY = y;
        for (Mod mod : mods) {
          if (yarrakizm == this.selectetModuleindex)
            Gui.drawRect(75, pantolonKiranModY, 160, pantolonKiranModY + 10, this.hoverColor); 
          Ready.fm.semosaksofon_t.drawStringWithShadow(mod.name, ((yarrakizm == this.selectetModuleindex) ? 78 : 76), pantolonKiranModY, mod.enabled ? this.hoverColor : 11184810);
          yarrakizm++;
          pantolonKiranModY += 10;
        } 
      } 
      y += 13;
      curIndex++;
      b++;
    } 
  }
  
  public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
    enableGL2D();
    glColor(internalColor);
    drawRect(x + width, y + width, x1 - width, y1 - width);
    glColor(borderColor);
    drawRect(x + width, y, x1 - width, y + width);
    drawRect(x, y, x + width, y1);
    drawRect(x1 - width, y, x1, y1);
    drawRect(x + width, y1 - width, x1 - width, y1);
    disableGL2D();
  }
  
  public static void glColor(int hex) {
    float alpha = (hex >> 24 & 0xFF) / 255.0F;
    float red = (hex >> 16 & 0xFF) / 255.0F;
    float green = (hex >> 8 & 0xFF) / 255.0F;
    float blue = (hex & 0xFF) / 255.0F;
    GL11.glColor4f(red, green, blue, alpha);
  }
  
  public static void drawRect(float x, float y, float x1, float y1) {
    GL11.glBegin(7);
    GL11.glVertex2f(x, y1);
    GL11.glVertex2f(x1, y1);
    GL11.glVertex2f(x1, y);
    GL11.glVertex2f(x, y);
    GL11.glEnd();
  }
  
  public static void enableGL2D() {
    GL11.glDisable(2929);
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glDepthMask(true);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4354);
    GL11.glHint(3155, 4354);
  }
  
  public static void enableGL3D() {
    GL11.glDisable(3008);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glEnable(2884);
    GL11.glEnable(2848);
    GL11.glHint(3154, 4353);
    GL11.glDisable(2896);
  }
  
  public static void disableGL3D() {
    GL11.glEnable(2896);
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glEnable(2929);
    GL11.glDisable(3042);
    GL11.glEnable(3008);
    GL11.glDepthMask(true);
    GL11.glCullFace(1029);
  }
  
  public static void disableGL2D() {
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glEnable(2929);
    GL11.glDisable(2848);
    GL11.glHint(3154, 4352);
    GL11.glHint(3155, 4352);
  }
  
  public void onKeyPress(int key) {
    switch (key) {
      case 200:
        if (this.isSemoteoOrospuCocugu) {
          if (--this.selectetModuleindex < 0)
            this.selectetModuleindex = getModsInCategory(Category.values()[this.selectedCategory]).size() - 1; 
          break;
        } 
        if (--this.selectedCategory < 0)
          this.selectedCategory = (Category.values()).length - 1; 
        break;
      case 208:
        if (this.isSemoteoOrospuCocugu) {
          if (++this.selectetModuleindex >= getModsInCategory(Category.values()[this.selectedCategory]).size())
            this.selectetModuleindex = 0; 
          break;
        } 
        if (++this.selectedCategory > (Category.values()).length - 1)
          this.selectedCategory = 0; 
        break;
      case 205:
        if (!this.isSemoteoOrospuCocugu) {
          this.selectetModuleindex = 0;
          this.isSemoteoOrospuCocugu = true;
          break;
        } 
        ((Mod)getModsInCategory(Category.values()[this.selectedCategory]).get(this.selectetModuleindex)).toggle();
        break;
      case 28:
        if (this.isSemoteoOrospuCocugu)
          ((Mod)getModsInCategory(Category.values()[this.selectedCategory]).get(this.selectetModuleindex)).toggle(); 
        break;
      case 203:
        this.isSemoteoOrospuCocugu = false;
        break;
    } 
  }
  
  public List<Mod> getModsInCategory(Category cat) {
    List<Mod> mods = new ArrayList<>();
    for (Mod mod : ModManager.getModules()) {
      if (mod.category == cat)
        mods.add(mod); 
    } 
    return mods;
  }
}
