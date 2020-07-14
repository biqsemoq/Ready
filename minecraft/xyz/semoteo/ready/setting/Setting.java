package xyz.semoteo.ready.setting;

public class Setting<E>
{
    public E value;
    public String name;
    
    public Setting(final String _name, final E _value) {
        this.value = _value;
        this.name = _name;
    }
}
