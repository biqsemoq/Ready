package xyz.semoteo.ready.mod.impl.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;

public class FastBow extends Mod {

	public FastBow() {
		super("FastBow", new String[]{"fastbow"});
		category = Category.Combat;
	}
	@EventListener
private void UpdateEvent(PlayerMotionEvent Event) {

	if (Event.state == Event.state.BEFORE && this.mc.thePlayer.getItemInUse() != null &&  this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow && this.mc.thePlayer.getItemInUseDuration() == 15) {
        for (int i = 0; i < 5; ++i) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
        this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        this.mc.thePlayer.stopUsingItem();
    }
}
}
