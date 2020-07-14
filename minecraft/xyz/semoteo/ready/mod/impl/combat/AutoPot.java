package xyz.semoteo.ready.mod.impl.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.setting.Setting;
import xyz.semoteo.ready.utils.TeoTimer;

public class AutoPot extends Mod {
  private Setting<Float> health;
  
  private Setting<Float> delay;
  
  private int yarrakTicks;
  
  private double tpX;
  
  private double tpY;
  
  private double tpZ;
  
  private TeoTimer timer;
  
  public AutoPot() {
    super("AutoPot", new String[] { "autopot", "ap" });
    this.health = new Setting("Health", Float.valueOf(4.0F));
    this.delay = new Setting("Delay", Float.valueOf(350.0F));
    this.timer = new TeoTimer();
    this.category = category.Combat;
  }
  
  public static TeoTimer AuraTimer = new TeoTimer();
  
  public static boolean doNextTick;
  public float lastYaw;
  @EventListener
  private void Update(PlayerMotionEvent event) {
    if (event.state == Event.State.BEFORE) {
        boolean state = (this.mc.thePlayer.getHealth() <= ((Float)this.health.value).floatValue() * 2.0D && 
        this.timer.delay(((Float)this.delay.value).floatValue()));
      if ((ModManager.getMod("Aura")).enabled && 
        this.mc.thePlayer.getHealth() <= ((Float)this.health.value).floatValue() * 2.0F && 
        this.timer.delay(((Float)this.delay.value).floatValue())) {
        state = doNextTick;
        lastYaw = event.getRotationYaw();
      } 
    } else if (event.state == Event.State.AFTER) {
      int slot = 8;
      if (getPotionFromInventory() != -1 && 
        this.mc.thePlayer.getHealth() <= ((Float)this.health.value).floatValue() * 2.0F && 
        this.timer.delay(((Float)this.delay.value).floatValue()) && mc.thePlayer.onGround) {
        this.timer.reset();
        AuraTimer.reset();
       
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(lastYaw, -90, mc.thePlayer.onGround));
        swap(getPotionFromInventory(), slot);
        this.mc.thePlayer.sendQueue
          .addToSendQueue((Packet)new C09PacketHeldItemChange(slot));
        this.mc.thePlayer.sendQueue
          .addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(
              this.mc.thePlayer.getHeldItem()));
        this.mc.thePlayer.sendQueue
          .addToSendQueue((Packet)new C09PacketHeldItemChange(
              this.mc.thePlayer.inventory.currentItem));
       
        mc.thePlayer.jump();
      mc.thePlayer.motionX = 0;
      mc.thePlayer.motionZ = 0;
        } 
      } 
      doNextTick = false;
    } 
  
  
  private int getPotionFromInventory() {
    int pot = -1;
    int counter = 0;
    for (int i = 1; i < 45; i++) {
      if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
        ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i)
          .getStack();
        Item item = is.getItem();
        if (item instanceof ItemPotion) {
          ItemPotion potion = (ItemPotion)item;
          if (potion.getEffects(is) != null)
            for (Object o : potion.getEffects(is)) {
              PotionEffect effect = (PotionEffect)o;
              if (effect.getPotionID() == Potion.heal.id && 
                ItemPotion.isSplash(is.getItemDamage())) {
                counter++;
                pot = i;
              } 
            }  
        } 
      } 
    } 
    return pot;
  }
  
  protected void swap(int slot, int hotbarNum) {
    this.mc.playerController.windowClick(
        this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, 
        (EntityPlayer)this.mc.thePlayer);
  }
}
