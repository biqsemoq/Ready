package xyz.semoteo.ready.mod.impl.combat;

import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.setting.*;
import xyz.semoteo.ready.utils.*;
import xyz.semoteo.ready.mod.*;
import xyz.semoteo.ready.eventapi.events.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.semoteo.ready.eventapi.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class AutoSoup extends Mod
{
    private Setting<Float> health;
    private Setting<Float> delay;
    public static TeoTimer timer;
    

    public AutoSoup() {
        super("AutoSoup", new String[] { "autosoup" });
        this.health = new Setting<Float>("Health", 4.0f);
        this.delay = new Setting<Float>("Delay", 350.0f);
        this.category = Category.Combat;
        timer = new TeoTimer();
    }
    
    		
    
    @EventListener
	private void Motion(PlayerMotionEvent event) {
    	if(event.state== Event.State.BEFORE) {
    		final int soupSlot = getSoup();
    		if(soupSlot != -1 &&(mc.thePlayer.getHealth() <= this.health.value * 2.0F) && (this.timer.delay((float)this.delay.value)))  {
    			swap(soupSlot, 7);
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(7));
				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				timer.reset();
    		}
    	}
	}



protected final void swap(int slot, int hotbarNum)
{
    this.mc.playerController.windowClick(
            this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, 
            (EntityPlayer)this.mc.thePlayer);
}


public int getSoup() {
	  int pot = -1;
	    int counter = 0;
	    for (int i = 1; i < 45; i++) {
	      if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
	        ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i)
	          .getStack();
	        
	        Item item = is.getItem();
	        if (item instanceof ItemSoup) {
	        	return i;
	        }
	      }
	    }
	    return -1;
}
}
