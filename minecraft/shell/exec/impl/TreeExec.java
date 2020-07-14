package shell.exec.impl;

import shell.Log;
import shell.LogType;
import shell.exec.Exec;

public class TreeExec extends Exec {

    public TreeExec() {
        super(new String[]{"tree"}, new String[]{}, "Tree");
    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        String result = getShell().executeCMD("tree");
        write(new Log(result, LogType.SUCCESS));
    }
}
