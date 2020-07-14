package xyz.semoteo.ready.eventapi.events;

import xyz.semoteo.ready.eventapi.Event;


	public class KeyEvent extends Event
	{
	  private int key;
	  
	  public KeyEvent(int key)
	  {
	    this.key = key;
	  }
	  
	  public int getKey()
	  {
	    return this.key;
	  }
	  
	  public void setKey(int key)
	  {
	    this.key = key;
	  }
	}

