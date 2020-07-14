package xyz.semoteo.ready.mod.impl.player;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.Event.State;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;

public class Sneak extends Mod {

	public Sneak() {
		super("Sneak", new String[]{"sneak"});
	category = Category.Player;
	}
	@EventListener
private void UpdateEvent(PlayerMotionEvent event) {
	if(event.state == State.BEFORE) {
		mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
	} else if (event.state == State.AFTER) {
		mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}
}
 private void disable() {
	 if(mc.thePlayer == null) {
		 mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING)); 
	super.onDisabled();
	 }
 }
}
