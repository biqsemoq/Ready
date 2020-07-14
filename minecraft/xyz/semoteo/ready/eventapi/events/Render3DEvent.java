package xyz.semoteo.ready.eventapi.events;

import xyz.semoteo.ready.eventapi.Event;

public class Render3DEvent extends Event
{
    private float partialTicks;
    
    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
}