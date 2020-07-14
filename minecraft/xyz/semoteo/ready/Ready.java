package xyz.semoteo.ready;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.opengl.Display;

import shell.Shell;
import shell.conf.ShellConfig;
import xyz.semoteo.ready.cmds.BindExec;
import xyz.semoteo.ready.cmds.CommandsManager;
import xyz.semoteo.ready.font.FontManager;
import xyz.semoteo.ready.friend.FriendManager;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.setting.SettingManager;

public class Ready {

	public static final String PREFIX = "-";
	public static FontManager fm;
	public static Shell shell;
	
	public static void startClient() {
		fm = new FontManager(); 
		fm.setup();
		ModManager.setup();
		CommandsManager.Start();
		SettingManager.init();
		FriendManager.start();
		Display.setTitle("READY CLIENT");
		shell = new Shell(ShellConfig.loadTheme("default"));
		shell.getExecuteableList().add(new BindExec());
		shell.useConfig(ShellConfig.loadTheme("teo"));
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {

				ModManager.saveModules();
				SettingManager.save();
				
			}
		}));
		
	}			

	  
	
	public static final void LOG(String txt) {
		Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§3[§fReady§3]:§r " + txt));
	}
	
}
