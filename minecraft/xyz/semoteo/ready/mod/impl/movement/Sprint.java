package xyz.semoteo.ready.mod.impl.movement;

import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.SprintEvent;
import xyz.semoteo.ready.eventapi.events.TickEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.utils.TeoTimer;

public class Sprint extends Mod {
  public Sprint() {
    super("Sprint", new String[] { "Sp" });
    this.category = category.Movement;
  }
  private TeoTimer timer;
  @EventListener
  public void Tick(TickEvent event) {
    if (this.mc.thePlayer != null)
      this.mc.thePlayer.setSprinting(shouldSprint());
  }
  
  @EventListener
  private void SprintEvent(SprintEvent sprint) {
    sprint.sprint = shouldSprint();
  }
  
  public boolean shouldSprint() {
    return (!this.mc.thePlayer.isSneaking() && !this.mc.thePlayer.isCollidedHorizontally && (this.mc.thePlayer.moveForward != 0.0F || this.mc.thePlayer.moveStrafing != 0.0F));
  }
}
