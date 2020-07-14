package xyz.semoteo.ready.cmds;

import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.setting.Setting;

public class SettingCommands extends Commands {
  public SettingCommands() {
    super(new String[] { "#BAKAMASSINKKODLAR GOROSPU COCUGU" }, "#bakma amk bakma");
  }
  
  public String RunCmd(String[] args) {
    for (Mod mod : ModManager.getModules()) {
      if (args[0].equalsIgnoreCase(mod.name)) {
        if (mod.settings.isEmpty())
          return "MODULENIN AYARI YOKKI AMK MALMISIN  NESIN"; 
        for (Setting set : mod.settings) {
          if (set.name.equalsIgnoreCase(args[1])) {
            String strValue = (args.length < 2) ? "" : args[2];
            if (set.value instanceof Integer) {
              set.value = Integer.valueOf(Integer.parseInt(strValue));
            } else if (set.value instanceof Boolean) {
              set.value = Boolean.valueOf(Boolean.parseBoolean((strValue == "") ? (new StringBuilder(String.valueOf(!((Boolean)set.value).booleanValue()))).toString() : strValue));
            } else if (set.value instanceof Float) {
              set.value = Float.valueOf(Float.parseFloat(strValue));
            } else if (set.value instanceof Long) {
              set.value = Long.valueOf(Long.parseLong(strValue));
            } else if (set.value instanceof Double) {
              set.value = Double.valueOf(Double.parseDouble(strValue));
            } else if (set.value instanceof String) {
              set.value = strValue;
            } else if (set.value instanceof Enum) {
                try {
                    Object[] enumConstants;
                    for (int length = (enumConstants = ((Enum)set.value).getDeclaringClass().getEnumConstants()).length, i = 0; i < length; ++i) {
                        final Object enu = enumConstants[i];
                        final Enum en = (Enum)enu;
                        if (en.name().equalsIgnoreCase(strValue)) {
                            set.value = en;
                        }
                    }
                }
                catch (Exception ex) {}
            }
            return String.valueOf(mod.name) + " nin " + set.name + " ayari " + set.value + " ye ayarlandi";
        }
    }
    return "one lan";
}
}
return "OYLE MODULEMI VAR AMK?";
}
}
