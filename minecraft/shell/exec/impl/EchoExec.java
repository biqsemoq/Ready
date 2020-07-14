package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

public class EchoExec extends Exec {

    public EchoExec() {
        super(new String[]{"echo"}, new String[]{}, "Echos the input");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        String reply = fullText.substring(4).trim();
        write(new Log(reply, LogType.SUCCESS));
    }
}
