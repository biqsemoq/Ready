package xyz.semoteo.ready.utils;

import java.util.List;

import xyz.semoteo.ready.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RotationUtils {

	
	  public static float[] getRotations(Entity ent)
	  {
	    double x = ent.posX;
	    double z = ent.posZ;
	    double y = ent.boundingBox.maxY - 4.0;
	    return getRotationFromPosition(x, z, y);
	  }
	  
	  public static float[] getAverageRotations(List<EntityLivingBase> targetList)
	  {
	    double posX = 0.0D;
	    double posY = 0.0D;
	    double posZ = 0.0D;
	    for (Entity ent : targetList)
	    {
	      posX += ent.posX;
	      posY += ent.boundingBox.maxY - 5.0D;
	      posZ += ent.posZ;
	    }
	    posX /= targetList.size();
	    posY /= targetList.size();
	    posZ /= targetList.size();
	    
	    return new float[] { getRotationFromPosition(posX, posZ, posY)[0], getRotationFromPosition(posX, posZ, posY)[1] };
	  }
	  
	  public static float[] getRotationFromPosition(double x, double z, double y)
	  {
	    double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
	    double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
	    double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
	    
	    double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
	    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
	    return new float[] { yaw, pitch };
	  }
	  
	  public static float getTrajAngleSolutionLow(float d3, float d1, float velocity)
	  {
	    float g = 0.006F;
	    float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0F * d1 * (velocity * velocity));
	    return (float)Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
	  }

	  public static float[] getRotationsAtLocation(Location loc, Entity entity) {
	    double positionX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
	    double positionZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
	    double locationMath = 0.0D;
	    switch (loc) {
	      case HEAD:
	        locationMath = 1.0D;
	        break;
	      case CHEST:
	        locationMath = 1.3D;
	        break;
	      case LEG:
	        locationMath = 2.9D;
	        break;
	      case FEET:
	        locationMath = 4.0D;
	        break;
	    } 
	    double positionY = entity.posY + entity.getEyeHeight() / locationMath - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
	    double positions = MathHelper.sqrt_double(positionX * positionX + positionZ * positionZ);
	    float yaw = (float)(Math.atan2(positionZ, positionX) * 180.0D / Math.PI) - 90.0F;
	    float pitch = (float)-(Math.atan2(positionY, positions) * 180.0D / Math.PI);
		return null;
	  
	  }
	  public static float getNewAngle(float angle)
	  {
	    angle %= 360.0F;
	    if (angle >= 180.0F) {
	      angle -= 360.0F;
	    }
	    if (angle < -180.0F) {
	      angle += 360.0F;
	    }
	    return angle;
	  }
	  
	  public static float getDistanceBetweenAngles(float angle1, float angle2)
	  {
	    float angle = Math.abs(angle1 - angle2) % 360.0F;
	    if (angle > 180.0F) {
	      angle = 360.0F - angle;
	    }
	    return angle;
	  }
	
}
