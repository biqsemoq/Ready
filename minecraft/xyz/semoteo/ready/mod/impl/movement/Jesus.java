package xyz.semoteo.ready.mod.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.PlayerMotionEvent;
import xyz.semoteo.ready.eventapi.events.TickEvent;
import xyz.semoteo.ready.mod.Mod;

public class Jesus extends Mod {
  public Jesus() {
    super("Jesus", new String[] { "jesus" });
    this.category = category.Movement;
  }
  
  @EventListener
  private void Motion(PlayerMotionEvent e) {
    if (isInLiquid() && 
      this.mc.thePlayer.isBurning() && this.mc.thePlayer.isInWater())
      e.setY(e.getY() - 0.01D); 
  }
  
  @EventListener
  private void Tick(TickEvent event) {
    boolean teo = false;
    if (isInLiquid() && !this.mc.thePlayer.isSneaking())
      if (teo = !teo) {
        this.mc.thePlayer.motionY = teo ? 0.06D : (this.mc.gameSettings.keyBindJump.pressing ? 0.4D : 0.3D);
        if (this.mc.thePlayer.moveForward == 0.0F && this.mc.thePlayer.moveStrafing == 0.0F) {
          this.mc.thePlayer.motionX = 0.0D;
          this.mc.thePlayer.motionZ = 0.0D;
        } 
      }  
  }
  
  public static boolean isInLiquid() {
    if ((Minecraft.getMinecraft()).thePlayer == null)
      return false; 
    boolean inLiquid = false;
    int y = (int)(Minecraft.getMinecraft()).thePlayer.boundingBox.minY;
    for (int x = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minX); x < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minZ); z < MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxZ) + 1; z++) {
        Block block = (Minecraft.getMinecraft()).theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
        if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
          if (!(block instanceof net.minecraft.block.BlockLiquid))
            return false; 
          inLiquid = true;
        } 
      } 
    } 
    return inLiquid;
  }
}
