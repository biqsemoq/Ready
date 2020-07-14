package xyz.semoteo.ready.eventapi.events;

import xyz.semoteo.ready.eventapi.Event;

public class SprintEvent extends Event{
	public boolean sprint;
	  
	  public SprintEvent(boolean sprint)
	  {
	    this.sprint = sprint;
	  }
}
