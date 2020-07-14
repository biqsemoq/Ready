package xyz.semoteo.ready.eventapi.events;

import xyz.semoteo.ready.eventapi.Event;

public class Render2DEvent extends Event {

	  public int width;
	  public int height;
	  
	  public Render2DEvent(int width, int height)
	  {
	    this.width = width;
	    this.height = height;
	  }
	  
	  public int getWidth()
	  {
	    return this.width;
	  }
	  
	  public void setWidth(int width)
	  {
	    this.width = width;
	  }
	  
	  public int getHeight()
	  {
	    return this.height;
	  }
	  
	  public void setHeight(int height)
	  {
	    this.height = height;
	  }
}
