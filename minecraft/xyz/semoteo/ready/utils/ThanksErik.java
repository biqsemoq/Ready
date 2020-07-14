package xyz.semoteo.ready.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ThanksErik {
  public static float[] getBlockRotations(int x, int y, int z, EnumFacing facing) {
    Minecraft mc = Minecraft.getMinecraft();
    EntitySnowball entitySnowball = new EntitySnowball((World)mc.theWorld);
    ((Entity)entitySnowball).posX = x + 0.5D;
    ((Entity)entitySnowball).posY = y + 0.5D;
    ((Entity)entitySnowball).posZ = z + 0.5D;
    return getAngles((Entity)entitySnowball);
  }
  
  public static float[] getAngles(Entity e) {
    Minecraft mc = Minecraft.getMinecraft();
    return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
  }
  
  public static float[] getRotations(Entity entity) {
    double diffY1;
    if (entity == null)
      return null; 
    double diffX = entity.posX - (Minecraft.getMinecraft()).thePlayer.posX;
    double diffZ = entity.posZ - (Minecraft.getMinecraft()).thePlayer.posZ;
    if (entity instanceof EntityLivingBase) {
      EntityLivingBase elb = (EntityLivingBase)entity;
      diffY1 = elb.posY + 
        elb.getEyeHeight() - 0.4D - 
        (Minecraft.getMinecraft()).thePlayer.posY + (Minecraft.getMinecraft()).thePlayer
        .getEyeHeight();
    } else {
      diffY1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 
        2.0D - 
        (Minecraft.getMinecraft()).thePlayer.posY + (Minecraft.getMinecraft()).thePlayer
        .getEyeHeight();
    } 
    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY1, dist) * 180.0D / Math.PI);
    return new float[] { yaw, pitch };
  }
  
  public static float getYawChangeToEntity(Entity entity) {
    Minecraft mc = Minecraft.getMinecraft();
    double deltaX = entity.posX - mc.thePlayer.posX;
    double deltaZ = entity.posZ - mc.thePlayer.posZ;
    double yawToEntity1 = 0.0D;
    if (deltaZ < 0.0D && deltaX < 0.0D) {
      yawToEntity1 = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else if (deltaZ < 0.0D && deltaX > 0.0D) {
      double yawToEntity2 = -90.0D + 
        Math.toDegrees(Math.atan(deltaZ / deltaX));
    } else {
      double d = Math.toDegrees(-Math.atan(deltaX / deltaZ));
    } 
    return 
      MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity1));
  }
  
  public static float getPitchChangeToEntity(Entity entity) {
    Minecraft mc = Minecraft.getMinecraft();
    double deltaX = entity.posX - mc.thePlayer.posX;
    double deltaZ = entity.posZ - mc.thePlayer.posZ;
    double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4D - 
      mc.thePlayer.posY;
    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * 
        deltaZ);
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - 
        (float)pitchToEntity);
  }
}
