package xyz.semoteo.ready.mod.impl.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PacketReceiveEvent;
import xyz.semoteo.ready.mod.Mod;

public class NoKnockback extends Mod {

	public NoKnockback() {
		super("NoKnockback", new String[]{"kb","antivelocity","velocity"});
		category = Category.Combat;
	}

	 @EventListener
	  private void onPacketReceive(PacketReceiveEvent event)
	  {
	    if ((event.packet instanceof S12PacketEntityVelocity)) {
	    	event.setCancelled(true);
	    }
	  }
	
}
