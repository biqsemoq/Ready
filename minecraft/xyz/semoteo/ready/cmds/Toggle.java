package xyz.semoteo.ready.cmds;

import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;

public class Toggle extends Commands {
  public Toggle() {
    super(new String[] { "toggle", "t" }, "Toggle");
  }
  
  public String RunCmd(String[] args) {
    String modName = "";
    if (args.length > 1)
      modName = args[1]; 
    Mod module = ModManager.getMod(modName);
    if (module == null)
      return "Module not found."; 
    module.toggle();
    return module.enabled ? ("IYI ACTIK AMK " + module.name) : ("KAPATTIK AMK BAGIRMA SIKICEM " + module.name);
  }
  
  public String getHelp() {
    return "Toggle - toggle <t, tog> (module) - Toggles a module on or off";
  }
}
