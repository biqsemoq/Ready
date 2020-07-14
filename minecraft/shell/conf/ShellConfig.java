package shell.conf;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// BAKMASANA KODLARA AMK
public abstract class ShellConfig {

    public int SHELL_TEST_VAR = 1;
    public int SHELL_WIDTH = 350;
    public int SHELL_HEIGHT = 220;

    public int SHELL_BACK_COLOR = 0xff000000;
    public int SHELL_BLACKOUT_COLOR = 0x80000000;
    public int SHELL_BORDER_COLOR = 0xff202020;
    public int SHELL_UP_INSIDE = 0xff000000;
    public int SHELL_UP_BORDER = 0xff202020;
    public int SHELL_OWNER_COLOR = -1;
    public int POINTER_BORDER_COLOR = 0xffBA55D3;
    public int POINTER_INSIDE_COLOR = 0xffBA55D3;
    public int EXIT_BUTTON_OVER_COLOR = 0xffff0000;
    public int EXIT_BUTTON_COLOR = 0xffcc0000;
    public int PROGRESS_BAR_COLOR = 0xff11ff11;
    public int POINTER_BLINK_TIME = 20;


    public String SHELL_FONT = "Liberation Sans";
    public String OWNER = "slowcheet4h";
    public String CLIENT_NAME = "streax";
    public String SEPERATOR = "@";
    public String SUFFIX = "§d$client$$seperator$$owner$ §f ~";

    public int SHELL_SUCCESS_COLOR = 0xff00ee00;
    public int SHELL_WARNING_COLOR = 0xffffff00;
    public int SHELL_ERROR_COLOR = 0xffee0000;
    public int SHELL_NEUTRAL_COLOR = -1;
    public int SHELL_SPECIAL_COLOR = 0xff0000ff;



    public boolean BLACK_OUT = true;


    public abstract void setup();

    public static void createDefaultTheme(File file) {
        try {
            ShellConfig shellConfig = new ShellConfig() {
                @Override
                public void setup() {

                }
            };
            List<String> write = new ArrayList<>();
            Field[] fields = ShellConfig.class.getFields();
            for (Field field : fields) {
                write.add(field.getName() + ":" + field.get(shellConfig));
            }
            write(file, write, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ShellConfig loadTheme(String name) {
        return loadTheme(ShellConfig.getTheme(name));
    }

    public static ShellConfig loadTheme(File file) {
        try {
            List<String> lines = read(file);
            ShellConfig shellConfig = new ShellConfig() {
                @Override
                public void setup() {

                }
            };
            Class<?> integer = Integer.TYPE;
            for (String line : lines) {
                if (line.startsWith("#") || !line.contains(":")) {
                    continue;
                }
                String[] split = line.split(":");
                String name = split[0];
                String value = split[1];

                try {
                    Field field = shellConfig.getClass().getField(name);
                    if (field.getType().isAssignableFrom(integer)) {
                        field.set(shellConfig, Integer.parseInt(value));
                    } else if (field.getType().isAssignableFrom(String.class)) {
                        field.set(shellConfig, value);
                    }
                } catch (NoSuchFieldException ex) {
                    ex.printStackTrace();
                }
            }
            return shellConfig;
        } catch (Exception ex) {
            ex.printStackTrace();
            return loadTheme(getTheme("default"));
        }
    }


    public static void write(File outputFile, List<String> writeContent, boolean overrideContent)
    {
        try
        {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
            for (String outputLine : writeContent) {
                out.write(outputLine + System.getProperty("line.separator"));
            }
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static List<String> read(File inputFile)
    {
        List<String> readContent = new ArrayList<>();
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
            String str;
            while ((str = in.readLine()) != null)
            {
                readContent.add(str);
            }
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return readContent;
    }

    public static File getShellConfig()
    {
        File file = new File(System.getenv("APPDATA"), ".shell");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getTheme(String name)
    {
        File file = new File(getShellConfig(), String.format("%s.txt", new Object[] { name }));
        if (!file.exists()) {
            try
            {
                file.createNewFile();
                createDefaultTheme(file);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return file;
    }
}
