package xyz.semoteo.ready.cmds;


import org.lwjgl.input.Keyboard;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;

public class BindExec extends Exec {

	
	public BindExec(){
		super(new String[]{"bind", "b"}, new String[]{"module", "key"}, "Binds module to key");
	}
	
	@Override
	public void runExec(String fullText, String[] splitted) {
		if (!hasInput("module")) {
			write(new Log("Amk module ismi gir sikicem", LogType.WARNING));
			return;
		}
		
		String modName = getInput("module");
		Mod mod = ModManager.getMod(modName);
		if (mod == null) {
			write(new Log("Aklini sikeyim boyle module yok", LogType.ERROR));
			return;
		}
		if (!hasInput("key")) {
			write(new Log("Amk tus girmemissin anani sikmiyim cabuk gir", LogType.WARNING));
			return;
		}
		
		int key = Keyboard.getKeyIndex(getInput("key").toUpperCase());
		mod.setKey(key);
		write(new Log(mod.name + " basariyla " + Keyboard.getKeyName(key) + " e bindlendi", LogType.SUCCESS));
	}
	
}
