package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

public class ClearExec extends Exec {

    public ClearExec() {
        super(new String[]{"clear"}, new String[]{"count"}, "Clears the shell");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        if (hasInput("count")) {
            int count = Integer.parseInt(getInput("count"));
            int removedCount = getShell().removeLogs(count);
            write(new Log("Removed " + removedCount, LogType.SUCCESS));
        } else {

            clear();
            if (checkSetting("test")) {
                write(new Log("TEST", LogType.WARNING));
            }
            write(new Log("Shell cleared", LogType.SUCCESS));
        }
    }
}
