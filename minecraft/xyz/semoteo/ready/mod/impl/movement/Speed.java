package xyz.semoteo.ready.mod.impl.movement;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.utils.TeoTimer;

public class Speed extends Mod {
  boolean fast;
  
  boolean NCP;
  
  public Speed() {
    super("Speed", new String[] { "speed" });
    this.fast = true;
    this.category = category.Movement;
  }
  private TeoTimer timer;
  @EventListener
  private void Update(PlayerMotionEvent event) {
    if (event.state == Event.State.BEFORE && (
      this.mc.thePlayer.moveForward != 0.0F || (this.mc.thePlayer.moveStrafing != 0.0F && !this.mc.thePlayer.movementInput.jump && !this.mc.thePlayer.isInWater() && this.fast))) {
      boolean speedticks = (this.mc.thePlayer.ticksExisted % 2 == 0);
      
      if(mc.thePlayer.onGround) {
        if (!speedticks) {
          double off = (this.mc.theWorld.getBlockState((new BlockPos((Entity)this.mc.thePlayer)).add(0, 2, 0)).getBlock() == Blocks.air) ? 0.4D : 0.2D;
          event.setY(event.getY() + off);
          event.ground = false;
        } 
        this.mc.thePlayer.setSpeed(this.mc.thePlayer.getSpeed() * (speedticks ? 3.29D : 0.7058D));
      } 
    } 
  }
}
