package xyz.semoteo.ready.mod.impl.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.eventapi.events.SlowlessEvent;
import xyz.semoteo.ready.mod.Mod;

public class NoSlowdown extends Mod {
  private boolean ground;
  
  public NoSlowdown() {
    super("NoSlowdown", new String[] { "noslow", "itemrun", "antislow" });
    this.category = category.Movement;
  }
  
  @EventListener
  public void ItemRun(SlowlessEvent slowItem) {
    slowItem.setCancelled(true);
  }
  
  @EventListener
  private void onUpdate(PlayerMotionEvent motionUpdate) {
   if(mc.thePlayer.isBlocking()) {
	  if (mc.thePlayer.isMoving()) {
      if (motionUpdate.state == Event.State.BEFORE) {
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      } else if (motionUpdate.state == Event.State.AFTER) {
    	  mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
      }  
    }  else {
    	 mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
    }
   }
  }
}
