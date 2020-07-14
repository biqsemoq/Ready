package xyz.semoteo.ready.mod.impl.movement;

import org.lwjgl.input.Keyboard;

import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.Render2DEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.utils.TeoTimer;

public class Inventory extends Mod {
  public Inventory() {
    super("Inventory", new String[] { "inventory,invmove" });
    this.category = Category.Movement;
  }
  private TeoTimer timer;
  @EventListener
  private void Render(Render2DEvent event) {
    if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
    	 
    	if (Keyboard.isKeyDown(200))
        this.mc.thePlayer.rotationPitch -= 2.0F; 
      if (Keyboard.isKeyDown(208))
        this.mc.thePlayer.rotationPitch += 2.0F; 
      if (Keyboard.isKeyDown(203))
        this.mc.thePlayer.rotationYaw -= 3.0F; 
      if (Keyboard.isKeyDown(205))
        this.mc.thePlayer.rotationYaw += 3.0F; 
    } 
  }
}
