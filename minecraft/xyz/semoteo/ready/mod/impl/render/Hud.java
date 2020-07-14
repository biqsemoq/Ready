package xyz.semoteo.ready.mod.impl.render;

import net.minecraft.client.Minecraft;
import xyz.semoteo.ready.Ready;
import xyz.semoteo.ready.Tabgui;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.KeyDownEvent;
import xyz.semoteo.ready.eventapi.events.Render2DEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.setting.Setting;

public class Hud extends Mod {
  private Tabgui tabgui;
  
  private Setting<Boolean> random;
  
  public Hud() {
    super("Hud", new String[] { "", "" });
    this.tabgui = new Tabgui();
    this.random = new Setting("Random", Boolean.valueOf(false));
    this.category = category.Render;
  }
  
  @EventListener
  private void Render(Render2DEvent event) {
    Ready.fm.semosaksofon_t.drawStringWithShadow("Ready", 2.0F, 2.0F, -10567907);
    Ready.fm.semosaksofon_t.drawStringWithShadow(""+Minecraft.debugFPS, 30, 2, -1);
    Ready.fm.semosaksofon_t.drawStringWithShadow(""+mc.thePlayer.hurtResistantTime, 50, 2, -1);
    int y = 2;
    for (Mod m : ModManager.getModules()) {
      if (m.enabled && ((Boolean)this.random.value).booleanValue()) {
        Ready.fm.semosaksofon_t.drawStringWithShadow(m.name, event.width - Ready.fm.semosaksofon_t.getStringWidth(m.name) - 2.0F, y, m.Color);
        y += 10;
        continue;
      } 
  
      if (m.enabled) {
        Ready.fm.semosaksofon_t.drawStringWithShadow(m.name, event.width - Ready.fm.semosaksofon_t.getStringWidth(m.name) - 2.0F, y, -10567907);
        y += 10;
      } 
    } 
    this.tabgui.render();
  }
  
  @EventListener
  public void onKeyPress(KeyDownEvent e) {
    this.tabgui.onKeyPress(e.getKey());
  }
}
