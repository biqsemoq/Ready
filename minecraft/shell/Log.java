package shell;

public class Log {
    private LogType type;
    private String  text;

    public Log(final String _text, final LogType _type) {
        text = _text;
        type = _type;
    }

    public LogType getType() {
        return type;
    }

    public String getText() {
        return text;
    }


}

