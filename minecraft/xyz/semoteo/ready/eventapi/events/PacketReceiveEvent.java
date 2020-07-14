package xyz.semoteo.ready.eventapi.events;


import net.minecraft.network.Packet;
import xyz.semoteo.ready.eventapi.Event;

public class PacketReceiveEvent extends Event{
	

	  public Packet packet;
	  
	  public PacketReceiveEvent(Packet packet)
	  {
	    this.packet = packet;
	  }
	


}
