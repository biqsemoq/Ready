package xyz.semoteo.ready.eventapi;
// BAKMASANA KODLARA AMK
public class KeyDownEvent extends Event{
	
	private final int key;
	
	public KeyDownEvent(int _key) {
		this.key = _key;
	}
	
	public int getKey() {
		return key;
	}
}