package xyz.semoteo.ready.mod.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.Event.State;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PacketSendEvent;
import xyz.semoteo.ready.mod.Mod;

public class Criticals extends Mod {
	  public static boolean cancel;
	public Criticals() {
		super("Criticals", new String[]{"crits"});
		
	category = Category.Combat;
	}
	// BAKMASANA KODLARA AMK
	// BAKMASANA KODLARA AMK
	
	@EventListener
private void packet(PacketSendEvent event) {
if	(event.packet  instanceof C02PacketUseEntity && event.state == State.BEFORE) {
    C02PacketUseEntity co2 = (C02PacketUseEntity)event.packet;
    Entity entity = co2.getEntityFromWorld(this.mc.theWorld);
    if ((co2.getAction() == C02PacketUseEntity.Action.ATTACK) && (((EntityLivingBase)entity).hurtTime < 5)) {
    {
      if (cancel)
      {
        cancel = false;
        return;
      }
      crit();
       }
      }
  }
	}

void crit()
{

  if (!this.mc.thePlayer.isCollidedVertically) {
    return;
  }
  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.05000000074505806D, this.mc.thePlayer.posZ, false));
  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY +0.012511000037193298D, this.mc.thePlayer.posZ, false));
  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
}
}
