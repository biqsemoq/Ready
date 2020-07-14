// BAKMASANA KODLARA AMK
// BAKMASANA KODLARA AMK
// BAKMASANA KODLARA AMK

package xyz.semoteo.ready.eventapi;

public class MouseEvent extends Event
{
    private int key;
    
    public MouseEvent(final int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
}
