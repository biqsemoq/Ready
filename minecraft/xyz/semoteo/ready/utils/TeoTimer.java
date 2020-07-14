package xyz.semoteo.ready.utils;

public class TeoTimer {
	  private long prevMS;
	  
	  public TeoTimer()
	  {
	    this.prevMS = 0L;
	  }
	  
	  public boolean delay(float milliSec)
	  {
	    return (getTime() - this.prevMS) >= milliSec;
	  }
	  
	  public void reset()
	  {
	    this.prevMS = getTime();
	  }
	  
	  public long getTime()
	  {
	    return System.nanoTime() / 1000000L;
	  }
	  
	  public long getDifference()
	  {
	    return getTime() - this.prevMS;
	  }
}
