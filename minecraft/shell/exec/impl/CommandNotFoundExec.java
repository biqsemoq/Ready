package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

public class CommandNotFoundExec extends Exec {

    public CommandNotFoundExec() {
        super(new String[]{""}, new String[]{}, "Command not found");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        write(new Log("Command Not Found", LogType.ERROR));
    }
}
