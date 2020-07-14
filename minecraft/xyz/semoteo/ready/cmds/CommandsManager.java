package xyz.semoteo.ready.cmds;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager {
  private static List<Commands> cmdlist = new ArrayList<>();
  
  public static void Start() {
    cmdlist.add(new Bind());
    cmdlist.add(new Toggle());
    cmdlist.add(new FriendADD());
  }
  
  public static Commands getCommandFromText(String string) {
    String[] stringArray = string.toUpperCase().split(" ");
    Commands asd = null;
    for (Commands command : getcmdlist()) {
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = command.teo).length, b = 0; b < i; ) {
        String teo = arrayOfString[b];
        if (stringArray[0].toUpperCase().startsWith(teo.toUpperCase())) {
          System.out.print("KOMUT BULUNDU AMK");
          asd = command;
          break;
        } 
        b++;
      } 
    } 
    return asd;
  }
  
  public static List<Commands> getcmdlist() {
    return cmdlist;
  }
}
