package shell.exec;

import shell.Log;
import shell.Shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Exec {

    private String[] expectedInputs;
    private String[] triggers;
    private String description;
    private HashMap<String, String> inputs;
    private List<String> settings;
    private Shell shell;
    private boolean useThread;

    public Exec(String[] _triggers, String[] _expectedInputs, String _description) {
        triggers = _triggers;
        expectedInputs = _expectedInputs;
        description = _description;

        inputs = new HashMap<>();
        settings = new ArrayList<>();
    }

    public Exec(String[] _triggers, String[] _expectedInputs, String _description, boolean _useThread) {
        this(_triggers, _expectedInputs, _description);
        useThread = _useThread;
    }

    public void setup(final Shell _shell) {
        shell = _shell;
        inputs.clear();
        settings.clear();
    }

    public abstract void runExec(String fullText, String[] splitted);


    public void write(Log log) {
        shell.addLog(log);
    }

    public String getInput(final String name) {
        if (hasInput(name)) {
            return inputs.get(name);
        }
        return null;
    }

    public boolean hasInput(final String name) {
        return inputs.containsKey(name);
    }

    public void clear() {
        shell.clear();
    }

    public Shell getShell() {
        return shell;
    }

    public HashMap<String, String> getInputs() {
        return inputs;
    }

    public List<String> getSettings() {
        return settings;
    }

    public boolean checkSetting(final String name) {
        return settings.contains(name);
    }

    public String getDescription() {
        return description;
    }

    public String[] getExpectedInputs() {
        return expectedInputs;
    }

    public boolean isUseThread() {
        return useThread;
    }

    public String[] getTriggers() {
        return triggers;
    }

}
