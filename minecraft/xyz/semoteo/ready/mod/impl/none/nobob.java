package xyz.semoteo.ready.mod.impl.none;

import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;

public class nobob extends Mod {
  public nobob() {
    super("Nobob", new String[] { "nobob" });
    this.category = category.Misc;
  }
  
  @EventListener
  private void UpdateEvent(PlayerMotionEvent event) {
    this.mc.thePlayer.distanceWalkedModified = 0.0F;
  }
}
