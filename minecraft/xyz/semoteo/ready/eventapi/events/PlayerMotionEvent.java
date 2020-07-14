package xyz.semoteo.ready.eventapi.events;

import net.minecraft.network.Packet;
import xyz.semoteo.ready.eventapi.Event;

	public class PlayerMotionEvent
	  extends Event
	{
		private double oldPositionY;
		  private boolean cancel;
		  public float yaw;
		  public float pitch;
		  public double y;
		  public boolean ground;
		  public boolean send;
		  public Event.State state;
		  private Packet packet;
		  private boolean cancelPackets;
		  
		  public Packet getPacket() {
			return packet;
		}

		public void setPacket(Packet packet) {
			this.packet = packet;
		}

		public boolean isCancelPackets() {
			return cancelPackets;
		}

		public void setCancelPackets(boolean cancelPackets) {
			this.cancelPackets = cancelPackets;
		}

		public PlayerMotionEvent(float yaw, float pitch, double y, boolean ground, Event.State state)
		  {
		    this.yaw = yaw;
		    this.pitch = pitch;
		    this.oldPositionY = y;
		    this.y = y;
		    this.ground = ground;
		    this.state = state;
		    this.send = false;
		  }
		  
		  public float getRotationYaw()
		  {
		    return this.yaw;
		  }
		  
		  public void setRotationPitch(float pitch)
		  {
		    this.pitch = pitch;
		  }
		  
		  public void setRotationYaw(float yaw)
		  {
		    this.yaw = yaw;
		  }
		  
		  public float getRotationPitch()
		  {
		    return this.pitch;
		  }
		  
		  public double getY()
		  {
		    return this.y;
		  }
		  
		  public void setY(double y)
		  {
		    this.y = y;
		  }
		  
		  public Event.State getMotion()
		  {
		    return this.state;
		  }
		  
		  public double getOldPositionY()
		  {
		    return this.oldPositionY;
		  }
}