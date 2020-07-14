package xyz.semoteo.ready.mod.impl.render;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import xyz.semoteo.ready.Category;
import xyz.semoteo.ready.Ready;
import xyz.semoteo.ready.mod.Mod;

public class ShowShell extends Mod {

	public ShowShell() {
		super("ShowShell", new String[]{"sheel"});
	setKey(Keyboard.KEY_ADD);
	category = Category.Render;
	}
	
	@Override
	public void onEnabled() {
		if (Minecraft.getMinecraft().thePlayer != null) {
			Ready.shell.openShell();
		}
		super.onEnabled();
	}
	
	

}
