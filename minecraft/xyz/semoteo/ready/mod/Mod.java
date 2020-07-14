package xyz.semoteo.ready.mod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.EventManager;
import xyz.semoteo.ready.setting.Setting;

public class Mod {
  protected Minecraft mc;
  
  public static EntityPlayerSP p;
  
  public String name;
  
  public String[] alias;
  
  public int key = -1;
  
  public boolean enabled; // BAKMASANA KODLARA AMK
  
  public boolean shown;
  
  public int Color;
  
  public Category category;
  
  public List<Setting> settings = new ArrayList<>();
  
  public Mod(String name, String[] alias) {
    this.name = name;
    this.alias = alias;
  }
  
  public void toggle() {
    this.mc = Minecraft.getMinecraft();
    this.enabled = !this.enabled;
    if (this.enabled) {
      onEnabled();
    } else {
      onDisabled();
    } 
    System.out.println("TEST " + this.enabled);
  }
  
  public void setKey(int key) {
    this.key = key;
  }
  
  public void onEnabled() {
    EventManager.register(this);
  }
  
  public void onDisabled() {
    EventManager.unregister(this);
  }
}
