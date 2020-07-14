package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingExec extends Exec {

    public PingExec() {
        super(new String[]{"ping"}, new String[]{"ip", "times"}, "Ping/Pong", true);
    }

    @Override
    public void runExec(String fullText, String[] splitted) {

        if (!hasInput("ip")) {
            write(new Log("ip is not valid", LogType.WARNING));
            return;
        }
        String add = "";
        if (checkSetting("max")) {
            add = "-l 1200";
        }

        String ip = getInput("ip");
        int times = 3;
        if (hasInput("times")) {
            try {
                times = Integer.valueOf(getInput("times"));
            } catch (Exception ex) {
                write(new Log("Count is not valid", LogType.WARNING));
                return;
            }
        }

        String result = getShell().executeCMD("ping " + ip + " -n " + times + " " + add);
        write(new Log(result, LogType.SUCCESS));
    }


}
