package shell;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import shell.conf.ShellConfig;
import shell.exec.Exec;
import shell.exec.impl.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Shell {

    private List<String> lastCommands;
    public int lastCommandIndex;

    public boolean debug;
    public int progress;
    public String owner;
    private List<Log> logList;

    private List<Exec> executeableList;
    private ShellUI shellUI;
    private ShellConfig config;

    public Shell(ShellConfig shellConfig) {
        ShellConfig.getTheme("default");
        config = shellConfig;
        config.setup();

        shellUI = new ShellUI(this);

        executeableList = new ArrayList<>();
        logList = new CopyOnWriteArrayList<>();
        lastCommands = new ArrayList<>();


        executeableList.add(new ClearExec());
        executeableList.add(new PingExec());
        executeableList.add(new DebugExec());
        executeableList.add(new EchoExec());
        executeableList.add(new NetstatExec());
        executeableList.add(new ThemeExec());

    }

    public void onCommand(final String text) {
        lastCommands.add(text);
        if (lastCommands.size() >= 15) {
            lastCommands.remove(0);
        }
        final String triggerName = text.contains(" ") ? text.split(" ")[0] : text;
        final Exec exec = getExec(triggerName);
        exec.setup(this);
        if (text.contains(" ")) {
            int i = 0;
            final String[] splitted = text.replace(triggerName, "").trim().split(" ");
            for (String str : splitted) {
                if (str.startsWith("--")) {
                    exec.getSettings().add(str.replace("--", ""));
                }
            }

           for (String exceptedInput : exec.getExpectedInputs()) {
                if (splitted.length > i && !splitted[i].startsWith("--")) {
                    exec.getInputs().put(exceptedInput, splitted[i]);
                } else {
                    break;
                }
                i++;
            }
            if (exec.isUseThread()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exec.runExec(text, splitted);
                    }
                }).start();
            } else {
                exec.runExec(text, splitted);
            }
        } else {
            if (exec.isUseThread()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exec.runExec(text, new String[0]);
                    }
                }).start();
            } else {
                exec.runExec(text, new String[0]);
            }
        }

    }

    public void useConfig(ShellConfig shellConfig) {
        config = shellConfig;
        config.setup();
        shellUI = new ShellUI(this);

    }

    public Exec getExec(final String input) {
        for (Exec exec : executeableList) {
            for (String trigger : exec.getTriggers()) {
                if (input.equalsIgnoreCase(trigger)) {
                    return exec;
                }
            }
        }
        return new CommandNotFoundExec();
    }



    public String executeCMD(String command) {
        try {
            Process process = Runtime.getRuntime().exec(
                    command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0) {
                output.append(buffer, 0, i);
            }
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error command is not valid";
        }
    }

    public int removeLogs(int count) {
        int removedCount = 0;
        for (int i = logList.size() - 1; i >= 0; i--) {
            logList.remove(i);
            removedCount++;
            count--;
            if (count == 0) {
                break;
            }
        }
        return removedCount;
    }

    public void addLog(Log log) {
        int y = 10 * logList.size();

        List<String> list = shellUI.splitToLines(log.getText(), 0);
        for (String str : list) {
            if (str.contains("\n")) {
                String[] lines = str.split("\n");
                for (String line : lines) {
                    y += 10;
                    logList.add(new Log(line, log.getType()));
                }

                continue;
            }

            y += 10;
            logList.add(new Log(str, log.getType()));
        }
        if (logList.size() > 300) {
            logList.remove(0);
        }
        shellUI.scroller =  logList.size() * 10 - config.SHELL_HEIGHT + 40;
    }


    public List<Exec> getExecuteableList() {
        return executeableList;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void openShell() {
        Minecraft.getMinecraft().displayGuiScreen(shellUI);
    }

    public void clear() {
        logList.clear();
    }

    public List<String> getLastCommands() {
        return lastCommands;
    }

    public int getLastCommandIndex() {
        return lastCommandIndex;
    }
    public ShellConfig getConfig() {
        return config;
    }
}
