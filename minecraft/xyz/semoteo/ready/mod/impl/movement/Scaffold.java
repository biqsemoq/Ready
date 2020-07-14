package xyz.semoteo.ready.mod.impl.movement;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import xyz.semoteo.ready.eventapi.Event;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.utils.TeoTimer;
import xyz.semoteo.ready.utils.ThanksErik;

public class Scaffold extends Mod {
  private List<Block> invalid;
  
  private TeoTimer timer;
  
  private BlockData blockData;
  
  boolean placing;
  
  private int slot;
  
  public Scaffold() {
    super("Scaffold", new String[] { "scaffold" });
    this.invalid = Arrays.asList(new Block[] { Blocks.air, (Block)Blocks.water, (Block)Blocks.fire, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava });
    this.timer = new TeoTimer();
    this.category = category.Movement;
  }
  
  protected static Minecraft mc = Minecraft.getMinecraft();
  
  @EventListener
  public boolean onUpdate(PlayerMotionEvent event) {
    if (event.getMotion() == Event.State.BEFORE) {
      int tempSlot = getBlockSlot();
      this.blockData = null;
      this.slot = -1;
      if (!mc.thePlayer.isSneaking() && tempSlot != -1) {
        double x2 = Math.cos(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
        double z2 = Math.sin(Math.toRadians((mc.thePlayer.rotationYaw + 90.0F)));
        double xOffset = MovementInput.moveForward * 0.4D * x2 + MovementInput.moveStrafe * 0.4D * z2;
        double zOffset = MovementInput.moveForward * 0.4D * z2 - MovementInput.moveStrafe * 0.4D * x2;
        double x = mc.thePlayer.posX + xOffset, y = mc.thePlayer.posY - 1.0D, z = mc.thePlayer.posZ + zOffset;
        BlockPos blockBelow1 = new BlockPos(x, y, z);
        if (mc.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air) {
          this.blockData = getBlockData(blockBelow1, this.invalid);
          this.slot = tempSlot;
          if (this.blockData != null) {
            event.setRotationYaw(ThanksErik.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0]);
            event.setRotationPitch(ThanksErik.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1]);
          } 
        } 
      } 
    } else if (event.getMotion() == Event.State.AFTER && 
      this.blockData != null && this.timer.delay(75.0F) && this.slot != -1) {
      mc.rightClickDelayTimer = 3;
      if (mc.gameSettings.keyBindJump.pressed && MovementInput.moveForward == 0.0F && MovementInput.moveStrafe == 0.0F) {
        mc.thePlayer.jump();
        mc.thePlayer.motionY = 0.0D;
      } 
      boolean dohax = (mc.thePlayer.inventory.currentItem != this.slot);
      if (dohax)
        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(this.slot)); 
      if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ())))
        mc.thePlayer.swingItem(); 
      if (dohax)
        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem)); 
    } 
    return this.placing;
  }
  
  public static BlockData getBlockData(BlockPos pos, List list) {
    return 
      
      !list.contains((Minecraft.getMinecraft()).theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? new BlockData(pos.add(0, -1, 0), EnumFacing.UP) : (!list.contains((Minecraft.getMinecraft()).theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : (!list.contains((Minecraft.getMinecraft()).theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? new BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : (!list.contains((Minecraft.getMinecraft()).theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : (!list.contains((Minecraft.getMinecraft()).theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null))));
  }
  
  public static class BlockData {
    public BlockPos position;
    
    public EnumFacing face;
    
    public BlockData(BlockPos position, EnumFacing face) {
      this.position = position;
      this.face = face;
    }
  }
  
  public static Block getBlock(int x, int y, int z) {
    return (Minecraft.getMinecraft()).theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
  }
  
  private int getBlockSlot() {
    for (int i = 36; i < 45; i++) {
      ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
      if (itemStack != null && itemStack.getItem() instanceof net.minecraft.item.ItemBlock)
        return i - 36; 
    } 
    return -1;
  }
}
