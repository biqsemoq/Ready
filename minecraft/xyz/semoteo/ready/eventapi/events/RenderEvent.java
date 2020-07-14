package xyz.semoteo.ready.eventapi.events;

import xyz.semoteo.ready.eventapi.Event;

public class RenderEvent
extends Event
{
private float partialTicks;

public RenderEvent(float partialTicks)
{
  this.partialTicks = partialTicks;
}

public float getPartialTicks()
{
  return this.partialTicks;
}
}