package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.conf.ShellConfig;
import shell.exec.Exec;

public class ThemeExec extends Exec {

    public ThemeExec() {
        super(new String[]{"loadtheme", "th"}, new String[]{"name"}, "Loads Theme");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        if (!hasInput("name")) {
            write(new Log("Tema adi girilmedi", LogType.ERROR));
            return;
        }
        String name = getInput("name");
        getShell().useConfig(ShellConfig.loadTheme(ShellConfig.getTheme(name)));
        write(new Log("Theme loaded", LogType.SUCCESS));

    }
}
