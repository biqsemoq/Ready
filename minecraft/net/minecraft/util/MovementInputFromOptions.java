package net.minecraft.util;

import org.lwjgl.input.Keyboard;

import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.ModManager;
import xyz.semoteo.ready.mod.impl.movement.Inventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";

    public MovementInputFromOptions(GameSettings p_i1237_1_)
    {
        this.gameSettings = p_i1237_1_;
    }


    public void updatePlayerMoveState() {
      if ((ModManager.getMod("Inventory")).enabled && !((Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
        moveStrafe = 0.0F;
        moveForward = 0.0F;
        if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode()))
          moveForward++; 
        if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode()))
          moveForward--; 
        if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode()))
          moveStrafe++; 
        if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode()))
          moveStrafe--; 
        this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
        this.sneak = this.gameSettings.keyBindSneak.pressed;
        if (this.sneak) {
          moveStrafe = (float)(moveStrafe * 0.3D);
          moveForward = (float)(moveForward * 0.3D);
        } 
      } else {
        moveStrafe = 0.0F;
        moveForward = 0.0F;
        if (this.gameSettings.keyBindForward.isKeyDown())
          moveForward++; 
        if (this.gameSettings.keyBindBack.isKeyDown())
          moveForward--; 
        if (this.gameSettings.keyBindLeft.isKeyDown())
          moveStrafe++; 
        if (this.gameSettings.keyBindRight.isKeyDown())
          moveStrafe--; 
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (this.sneak) {
          moveStrafe = (float)(moveStrafe * 0.3D);
          moveForward = (float)(moveForward * 0.3D);
        } 
      } 
    }
  }