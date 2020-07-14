package shell.exec.impl;

import shell.exec.Exec;

public class DebugExec extends Exec {

    public DebugExec() {
        super(new String[]{"debug"}, new String[]{}, "Debug the console");

    }

    @Override
    public void runExec(String fullText, String[] splitted) {
        getShell().debug = !getShell().debug;
    }
}
