package xyz.semoteo.ready.eventapi.events;

import net.minecraft.network.Packet;
import xyz.semoteo.ready.eventapi.Event;

public class PacketSendEvent extends Event {

	
	  public Packet packet;
	  public Event.State state;
	  
	  public PacketSendEvent(Event.State state, Packet packet)
	  {
	    this.state = state;
	    this.packet = packet;
	  }
	}


