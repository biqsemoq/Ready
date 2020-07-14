package shell;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import shell.utils.NahrFont;
import shell.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShellUI extends GuiScreen {

    private int shellWidth;
    private int shellHeight;
    private boolean dragging;
    private float dragX, dragY;
    private Shell _shell;
    private NahrFont mainFont;
    public float shellPosX, shellPosY;


    private int blinkTicks;

    public int scroller;

    private int clickedPosX;
    private int clickedPosY;
    private int textPointer;
    private String commandInput;

    public ShellUI(final Shell shell) {
        _shell = shell;
        // BAKMASANA KODLARA AMK
        mainFont = new NahrFont(new Font(_shell.getConfig().SHELL_FONT, Font.PLAIN, 16), 14);
        commandInput = "";
        shellWidth = _shell.getConfig().SHELL_WIDTH;
        shellHeight = _shell.getConfig().SHELL_HEIGHT;
        shellPosX = this.width / 2 - shellWidth / 2;
        shellPosY = this.height / 2 - shellHeight / 2;
    }


    public String suffix;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        // BAKMASANA KODLARA AMK
        if (_shell.getConfig().BLACK_OUT) {
          RenderUtils.drawRect(0, 0, width, height, _shell.getConfig().SHELL_BLACKOUT_COLOR);
        }

        // BAKMASANA KODLARA AMK
        RenderUtils.drawBorderedRect(shellPosX, shellPosY, shellPosX + shellWidth, shellPosY + shellHeight, _shell.getConfig().SHELL_BACK_COLOR, _shell.getConfig().SHELL_BORDER_COLOR);



        // BAKMASANA KODLARA AMK
        RenderUtils.drawBorderedRect(shellPosX, shellPosY, shellPosX + shellWidth, shellPosY + 20, _shell.getConfig().SHELL_UP_INSIDE, _shell.getConfig().SHELL_UP_BORDER);
        // BAKMASANA KODLARA AMK
        mainFont.drawString(_shell.getConfig().OWNER + _shell.getConfig().SEPERATOR + _shell.getConfig().CLIENT_NAME, shellPosX + shellWidth / 2F - mainFont.getStringWidth(_shell.getConfig().OWNER + _shell.getConfig().SEPERATOR + _shell.getConfig().CLIENT_NAME) / 2, shellPosY + 3, NahrFont.FontType.SHADOW_THIN, _shell.getConfig().SHELL_OWNER_COLOR,0xff000000);
        int scrollSize = Mouse.getDWheel() / 7;
        if (_shell.getLogList().size() * 10 >= shellHeight - 20) {
            scroller -= scrollSize;
        }
        int logY =  20 - scroller;
        if (logY > 20) {
            scroller = 0;
            logY = 20;
        }
        int lastLogY = 20 - scroller + _shell.getLogList().size() * 10;
        if (lastLogY <= shellHeight - 20 && scrollSize < 0) {
            scroller += scrollSize;
            logY =  20 - scroller;
        }
        for (Log log : _shell.getLogList()) {
            if (3 + logY + 25 >  _shell.getConfig().SHELL_HEIGHT ||  logY < 20) {
                logY += 10;
                continue;
            }
            int color;
            switch (log.getType()) {
                case ERROR: {
                    color = _shell.getConfig().SHELL_ERROR_COLOR;
                    break;
                }
                case SUCCESS: {
                    color = _shell.getConfig().SHELL_SUCCESS_COLOR;
                    break;
                }
                case WARNING: {
                    color = _shell.getConfig().SHELL_WARNING_COLOR;
                    break;
                }
                case SPECIAL: {
                    color = _shell.getConfig().SHELL_SPECIAL_COLOR;
                    break;
                }
                default: {
                    color = _shell.getConfig().SHELL_NEUTRAL_COLOR;
                    break;
                }
            }
            if (_shell.debug) {
                float width = mainFont.getStringWidth(log.getText());
                RenderUtils.drawBorderedRect(shellPosX, shellPosY + 3 + logY, shellPosX + width + 2, shellPosY + logY + 12, 0, -1);
            }
            mainFont.drawString(log.getText(), shellPosX + 2, shellPosY + logY, color);

            logY += 10;
        }

        float cursorY = shellPosY + Math.min(Math.max(20, logY), shellHeight - 20);
        mainFont.drawString(suffix + commandInput, shellPosX + 2, cursorY, -1);
        int textEnd = 4 + (int)mainFont.getStringWidth(suffix + commandInput) + (int)shellPosX;


        // BAKMASANA KODLARA AMK
        RenderUtils.drawBorderedRect(textEnd, cursorY + 4, textEnd + 4, cursorY + 12, (blinkTicks > 5 ? _shell.getConfig().POINTER_INSIDE_COLOR : 0), _shell.getConfig().POINTER_BORDER_COLOR);

        // BAKMASANA KODLARA AMK
        boolean isCloseOver = mouseX >= shellPosX + shellWidth - 12 && mouseX <= shellPosX + shellWidth && mouseY >= shellPosY + 2 && mouseY <= shellPosY + 12;
        RenderUtils.drawRect(shellPosX + shellWidth - 10, shellPosY + 2, shellPosX + shellWidth - 2, shellPosY + 10, isCloseOver ? _shell.getConfig().EXIT_BUTTON_OVER_COLOR : _shell.getConfig().EXIT_BUTTON_COLOR);

        // BAKMASANA KODLARA AMK
        if (_shell.progress != 0) {
            float progressWidth = shellWidth / 100 * _shell.progress;
            RenderUtils.drawRect(shellPosX, shellPosY + shellHeight - 5, shellPosX + progressWidth, shellPosY + shellHeight, _shell.getConfig().PROGRESS_BAR_COLOR);
            GL11.glPushMatrix();
            GL11.glScalef(0.7F, 0.7F, 0.7F);
            mainFont.drawString(_shell.progress + "%", shellPosX * 1.4F + 7 - mainFont.getStringWidth(_shell.progress + "%") + progressWidth * 1.4F + 20, 1.4F * (shellPosY + shellHeight - 4) + 4, -1);
            GL11.glScalef(2,2,2);
            GL11.glPopMatrix();
        }

        if (_shell.debug) {
            mainFont.drawString("SHELL_BACK", shellPosX + shellWidth - 5 - mainFont.getStringWidth("SHELL_BACK"), shellPosY + 30, -1);
            mainFont.drawString("SHELL_BLACK_OUT", 2,2, -1);
            mainFont.drawString("SHELL_EXIT_BUTTON", shellPosX + shellWidth - 10, shellPosY + 2,-1);
            if (commandInput.length() > 0) {
                RenderUtils.drawBorderedRect(shellPosX + 2 + mainFont.getStringWidth(suffix), shellPosY +logY+ 3,  textEnd + 2, shellPosY + logY + 13, 0, -1);
                mainFont.drawString("commandInput", textEnd + 2 - mainFont.getStringWidth(suffix + commandInput) / 2, shellPosY + logY + 13, -1);
            }
            mainFont.drawString("SHELL_TOP", shellPosX, shellPosY, -1);
            mainFont.drawString("POINTER", textEnd + 4, shellPosY + logY + 10, -1);
            mainFont.drawString("SHELL_OWNER", shellPosX + shellWidth / 2F - mainFont.getStringWidth(_shell.getConfig().OWNER + _shell.getConfig().SEPERATOR + _shell.getConfig().CLIENT_NAME) / 2, shellPosY - 10, -1);

        }
    }

    public void updateScreen() {
        _shell.progress = 0;
        blinkTicks++;
        // BAKMASANA KODLARA AMK
        suffix = _shell.getConfig().SUFFIX.replace("$owner$", _shell.getConfig().OWNER).replace("$client$", _shell.getConfig().CLIENT_NAME).replace("$seperator$", _shell.getConfig().SEPERATOR);
        if (blinkTicks > _shell.getConfig().POINTER_BLINK_TIME){
            blinkTicks = 0;
        }
        super.updateScreen();
    }

    public List<String> splitToLines(String text, final int startX) {
        int addX = startX;
        List<String> lines = new ArrayList<>();
        String currentLine = "";
        for (char ch : text.toCharArray()) {
            if (mainFont.getStringWidth(currentLine) + addX + 7 > shellWidth) {
                lines.add(currentLine);
                currentLine = "";
                addX = 0;
            }
            currentLine += ch;
        }
        lines.add(currentLine);
        return lines;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_UP) {
            _shell.lastCommandIndex--;
            if (_shell.lastCommandIndex < 0) {
                _shell.lastCommandIndex = _shell.getLastCommands().size() -1;
            }
            commandInput = _shell.getLastCommands().get(_shell.getLastCommandIndex());
        } else if (keyCode == Keyboard.KEY_DOWN) {
            _shell.lastCommandIndex++;
            if (_shell.lastCommandIndex >= _shell.getLastCommands().size()) {
                _shell.lastCommandIndex = 0;
            }
            commandInput = _shell.getLastCommands().get(_shell.getLastCommandIndex());
        }
        else if (keyCode == Keyboard.KEY_BACK) {
            if (commandInput.length() > 0) {
                commandInput = commandInput.substring(0, commandInput.length() - 1);
            }
        } else if (keyCode == Keyboard.KEY_RETURN) {

            _shell.lastCommandIndex = 0;
            _shell.addLog(new Log(suffix  + commandInput, LogType.NEUTRAL));
            _shell.onCommand(commandInput);
            commandInput = "";
        } else {
            final String allowedChars = "- abcdefgğhıijklmnoöprsştuüvyzxqw<\\$€£>1234567890.*/+%&'^€|(){}?=_";
            if (allowedChars.contains((typedChar + "").toLowerCase())) {
                blinkTicks = 3;
                commandInput += typedChar;

                float commandWidth = mainFont.getStringWidth(commandInput) + (int)mainFont.getStringWidth(suffix) + 5;
                if (commandWidth >= shellWidth) {

                    _shell.addLog(new Log(suffix +  commandInput, LogType.NEUTRAL));
                    _shell.onCommand(commandInput);
                    commandInput = "";
                }
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (dragging) {
            shellPosX += (mouseX - dragX);
            shellPosY += (mouseY - dragY);

            if (shellPosX + shellWidth > width) {
                shellPosX -= (mouseX - dragX);
            }
            if (shellPosY + shellHeight > height) {
                shellPosY -= (mouseY - dragY);
            }
            if (shellPosX < 0) {
                shellPosX = 0;
            }
            if (shellPosY < 0) {
                shellPosY = 0;
            }

            dragX = mouseX;
            dragY = mouseY;
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }


    @Override
    public void initGui() {
        Mouse.getDWheel();
        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean isCloseOver = mouseX >= shellPosX + shellWidth - 12 && mouseX <= shellPosX + shellWidth && mouseY >= shellPosY + 2 && mouseY <= shellPosY + 12;
        if (isCloseOver) {
            this.mc.displayGuiScreen(null);
        }

        if (mouseX >= shellPosX && mouseX <= shellPosX + shellWidth && mouseY >= shellPosY && mouseY <= shellPosY + shellHeight) {
            dragX = mouseX;
            dragY = mouseY;
            dragging = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }


}
