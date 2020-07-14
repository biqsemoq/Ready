package xyz.semoteo.ready.cmds;

import org.lwjgl.input.Keyboard;

import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;


public class Bind extends Commands 
{
	
	public Bind() {
		super(new String[]{"bind","b"},"Bind");
	}
	
	@Override
	public String RunCmd(String[] text) {
		// BAKMASANA KODLARA AMK
		// BAKMASANA KODLARA AMK
		
		Mod mod = ModManager.getMod(text[1]);
		if(mod == null) {
			return "Mod not found!";
		}// BAKMASANA KODLARA AMK
		mod.setKey(Keyboard.getKeyIndex(text[2].toUpperCase()));
		
		return  mod.name + "'s bind was set to " + Keyboard.getKeyName(mod.key);
	}

}
