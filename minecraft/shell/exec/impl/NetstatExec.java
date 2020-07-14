package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

public class NetstatExec extends Exec {

    public NetstatExec() {
        super(new String[]{"netstat", "nt"}, new String[]{}, "Shows the network connections");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        String result = getShell().executeCMD("netstat -n");
        write(new Log(result, LogType.SUCCESS));
    }
}
