package xyz.semoteo.ready.mod.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.utils.TeoTimer;

public class Step extends Mod {
  private TeoTimer timer;
  
  public Step() {
    super("Step", new String[] { "step", "st" });
    this.timer = new TeoTimer();
    this.category = category.Movement;
  }
  
  @EventListener
  public void onUpdate(PlayerMotionEvent event) {
    if (event.getMotion() == Event.State.AFTER)
      if (this.mc.thePlayer.isCollidedHorizontally && this.timer.delay(100.0F) && !this.mc.thePlayer.isOnLadder()) {
        this.timer.reset();
        double[] pos = { 0.42D, 0.75D };
        byte b;
        int i;
        double[] arrayOfDouble1;
        for (i = (arrayOfDouble1 = pos).length, b = 0; b < i; ) {
          double y = arrayOfDouble1[b];
          this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ, true));
          b++;
        } 
        this.mc.thePlayer.stepHeight = 1.0F;
        this.mc.timer.timerSpeed = 0.3F;
        (new Thread(new Runnable() {
              public void run() {
                try {
                  Thread.sleep(100L);
                } catch (Exception exception) {}
                Step.this.mc.timer.timerSpeed = 1.0F;
              }
            })).start();
      } else {
        this.mc.thePlayer.stepHeight = 0.5F;
      }  
  }
}
