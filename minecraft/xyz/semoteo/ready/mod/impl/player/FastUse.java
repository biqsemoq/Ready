package xyz.semoteo.ready.mod.impl.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;

public class FastUse extends Mod{

	public FastUse() {
		super("FastUse", new String[]{"fastuse,asd"});
		category = Category.Player;
	}
	@EventListener
private void Motion(PlayerMotionEvent event) {
	if(event.state==Event.State.BEFORE) {
		if(mc.thePlayer.isUsingItem()) {
			if(mc.thePlayer.getItemInUseDuration() == 16 ) {
				for(int i = 0; i < 17; i++) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
					}
			mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
			}
		}
	}
}
}
