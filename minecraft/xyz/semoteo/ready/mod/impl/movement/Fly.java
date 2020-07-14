package xyz.semoteo.ready.mod.impl.movement;

import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;

public class Fly extends Mod {
  private boolean glide;
  
  public Fly() {
    super("Fly", new String[] { "fly", "flight" });
    this.glide = false;
    this.category = category.Movement;
  }
  
  @EventListener
  private void UpdateEvent(PlayerMotionEvent event) {
    this.mc.thePlayer.motionY = -0.001D;
    this.mc.thePlayer.onGround = true;
    if (this.mc.thePlayer.movementInput.jump) {
      this.mc.thePlayer.motionY = 0.3D;
    } else if (this.mc.thePlayer.movementInput.sneak) {
      this.mc.thePlayer.motionY = -0.3D;
    } 
    if (this.glide) {
      this.mc.thePlayer.motionY = -0.03D;
      this.mc.thePlayer.onGround = true;
    } 
  }
}
