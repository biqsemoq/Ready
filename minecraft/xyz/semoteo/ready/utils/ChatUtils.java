package xyz.semoteo.ready.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {


  public static void sendClientMessage(String message)
  {
    String prefix = "Teo ";
    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(prefix) + message));
  }
  
  public static void sendMessage(String message)
  {
    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
  }
}


