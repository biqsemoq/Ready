package shell;

public interface IShell {

    public void _setup();
    public void onSetup();
    public void onOpen();
    public void onClose();

    public void onKeyPress(final int key);
    public void draw(final float mouseX, final float mouseY);
    public void clickMouse(final float mouseX, final float mouseY);
    public void clickMouseMove(final float mouseX, final float mouseY, final int clickedBtn);
    public void clickRelease(final float mouseX, final float mouseY, final int clickedBtn);
}
