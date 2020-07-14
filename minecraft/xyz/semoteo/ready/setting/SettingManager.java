package xyz.semoteo.ready.setting;

import java.io.*;
import xyz.semoteo.ready.utils.*;
import xyz.semoteo.ready.mod.*;
import java.util.*;
import java.lang.reflect.*;

public class SettingManager
{
    private static File settingFile;
    
    public static void init() {
        SettingManager.settingFile = FileUtils.getConfigFile("settings");
        for (final Mod mod : ModManager.getModules()) {
            loadMod(mod);
        }
        load();
        save();
    }
    
    public static <E> void load() {
        final List<String> OKU_AMK_OKU = FileUtils.read(SettingManager.settingFile);
        for (final String str : OKU_AMK_OKU) {
            final String[] split = str.split("::");
            final Mod mod = ModManager.getMod(split[0]);
            if (mod != null) {
                for (final Setting set : mod.settings) {
                    if (set.name.equalsIgnoreCase(split[1])) {
                        final String strValue = split[2];
                        if (set.value instanceof Integer) {
                            set.value = Integer.valueOf(Integer.parseInt(strValue));
                            break;
                        }
                        if (set.value instanceof Boolean) {
                            set.value = Boolean.valueOf(Boolean.parseBoolean(strValue));
                            break;
                        }
                        if (set.value instanceof Float) {
                            set.value = Float.valueOf(Float.parseFloat(strValue));
                            break;
                        }
                        if (set.value instanceof Long) {
                            set.value = Long.valueOf(Long.parseLong(strValue));
                            break;
                        }
                        if (set.value instanceof Double) {
                            set.value = Double.valueOf(Double.parseDouble(strValue));
                            break;
                        }
                        if (set.value instanceof String) {
                            set.value = strValue;
                            break;
                        }
                        if (set.value instanceof Enum) {
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
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    public static void save() {
        final List<String> YAZ_AMK_YAZ = new ArrayList<String>();
        for (final Mod mod : ModManager.getModules()) {
            for (final Setting setting : mod.settings) {
                YAZ_AMK_YAZ.add(String.valueOf(mod.name) + "::" + setting.name + "::" + setting.value);
            }
        }
        FileUtils.write(SettingManager.settingFile, YAZ_AMK_YAZ, true);
    }
    
    public static void loadMod(final Mod mod) {
        Field[] declaredFields;
        for (int length = (declaredFields = mod.getClass().getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if (field.getType().isAssignableFrom(Setting.class)) {
                try {
                    field.setAccessible(true);
                    mod.settings.add((Setting)field.get(mod));
                }
                catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
