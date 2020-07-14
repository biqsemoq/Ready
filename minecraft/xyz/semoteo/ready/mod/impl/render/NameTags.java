package xyz.semoteo.ready.mod.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import xyz.semoteo.ready.eventapi.EventListener;
import xyz.semoteo.ready.eventapi.events.Render3DEvent;
import xyz.semoteo.ready.mod.Mod;
import xyz.semoteo.ready.mod.impl.RenderMethods;

public class NameTags extends Mod {
  private boolean health;
  
  private boolean armor;
  
  private boolean ping;
  
  public NameTags() {
    super("Nametags", new String[] { "nametags" });
    this.health = true;
    this.armor = true;
    this.ping = true;
    this.category = category.Render;
  }
  
  @EventListener
  public void RenderEvent(Render3DEvent event) {
    if (this.mc.thePlayer != null)
      for (Object o : this.mc.theWorld.playerEntities) {
        Entity entity = (Entity)o;
        if (entity instanceof EntityPlayer && entity != this.mc.thePlayer) {
          Minecraft.getMinecraft().getRenderManager();
          double x = interpolate(entity.lastTickPosX, entity.posX, event.getPartialTicks()) - RenderManager.renderPosX;
          Minecraft.getMinecraft().getRenderManager();
          double y = interpolate(entity.lastTickPosY, entity.posY, event.getPartialTicks()) - RenderManager.renderPosY;
          Minecraft.getMinecraft().getRenderManager();
          double z = interpolate(entity.lastTickPosZ, entity.posZ, event.getPartialTicks()) - RenderManager.renderPosZ;
          renderNameTag((EntityPlayer)entity, x, y, z, event.getPartialTicks());
        } 
      }  
  }
  
  private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
    double tempY = y;
    tempY += player.isSneaking() ? 0.5D : 0.7D;
    Entity camera = Minecraft.getMinecraft().getRenderViewEntity();
    double originalPositionX = camera.posX;
    double originalPositionY = camera.posY;
    double originalPositionZ = camera.posZ;
    camera.posX = interpolate(camera.prevPosX, camera.posX, delta);
    camera.posY = interpolate(camera.prevPosY, camera.posY, delta);
    camera.posZ = interpolate(camera.prevPosZ, camera.posZ, delta);
    double distance = camera.getDistance(x + (Minecraft.getMinecraft().getRenderManager()).viewerPosX, y + (Minecraft.getMinecraft().getRenderManager()).viewerPosY, z + (Minecraft.getMinecraft().getRenderManager()).viewerPosZ);
    int width = (Minecraft.getMinecraft()).fontRendererObj.getStringWidth(getDisplayName(player)) / 2;
    double scale = 0.0018D + 0.0018D * distance;
    if (distance <= 8.0D)
      scale = 0.0245D; 
    GlStateManager.pushMatrix();
    RenderHelper.enableStandardItemLighting();
    GlStateManager.enablePolygonOffset();
    GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
    GlStateManager.disableLighting();
    GlStateManager.translate((float)x, (float)tempY + 1.4F, (float)z);
    Minecraft.getMinecraft().getRenderManager();
    GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    Minecraft.getMinecraft().getRenderManager();
    GlStateManager.rotate(RenderManager.playerViewX, ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
    GlStateManager.scale(-scale, -scale, scale);
    GlStateManager.disableDepth();
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    RenderMethods.drawBorderedRectReliant((-width - 2), -((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT + 1), width + 2.0F, 1.5F, 0.001F, 1996488704, 1426063360);
    GlStateManager.enableAlpha();
    (Minecraft.getMinecraft()).fontRendererObj.func_175063_a(getDisplayName(player), -width, -((Minecraft.getMinecraft()).fontRendererObj.FONT_HEIGHT - 1), getDisplayColour(player));
    if (this.armor) {
      GlStateManager.pushMatrix();
      int xOffset = 0;
      int index;
      for (index = 3; index >= 0; index--) {
        ItemStack stack = player.inventory.armorInventory[index];
        if (stack != null)
          xOffset -= 8; 
      } 
      if (player.getCurrentEquippedItem() != null) {
        xOffset -= 8;
        ItemStack renderStack = player.getCurrentEquippedItem().copy();
        if (renderStack.hasEffect() && (renderStack.getItem() instanceof net.minecraft.item.ItemTool || renderStack.getItem() instanceof net.minecraft.item.ItemArmor))
          renderStack.stackSize = 1; 
        renderItemStack(renderStack, xOffset, -26);
        xOffset += 16;
      } 
      for (index = 3; index >= 0; index--) {
        ItemStack stack = player.inventory.armorInventory[index];
        if (stack != null) {
          ItemStack armourStack = stack.copy();
          if (armourStack.hasEffect() && (armourStack.getItem() instanceof net.minecraft.item.ItemTool || armourStack.getItem() instanceof net.minecraft.item.ItemArmor))
            armourStack.stackSize = 1; 
          renderItemStack(armourStack, xOffset, -26);
          xOffset += 16;
        } 
      } 
      GlStateManager.popMatrix();
    } 
    camera.posX = originalPositionX;
    camera.posY = originalPositionY;
    camera.posZ = originalPositionZ;
    GlStateManager.enableDepth();
    GlStateManager.enableLighting();
    GlStateManager.disableBlend();
    GlStateManager.enableLighting();
    GlStateManager.disablePolygonOffset();
    GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
    GlStateManager.popMatrix();
  }
  
  private void renderItemStack(ItemStack stack, int x, int y) {
    GlStateManager.pushMatrix();
    GlStateManager.depthMask(true);
    GlStateManager.clear(256);
    RenderHelper.enableStandardItemLighting();
    (Minecraft.getMinecraft().getRenderItem()).zLevel = -150.0F;
    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    GlStateManager.disableBlend();
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    GlStateManager.disableAlpha();
    GlStateManager.disableAlpha();
    GlStateManager.disableBlend();
    GlStateManager.enableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.enableAlpha();
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
    Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(stack, x, y);
    Minecraft.getMinecraft().getRenderItem().renderItemOverlays((Minecraft.getMinecraft()).fontRendererObj, stack, x, y);
    (Minecraft.getMinecraft().getRenderItem()).zLevel = 0.0F;
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableCull();
    GlStateManager.enableAlpha();
    GlStateManager.disableBlend();
    GlStateManager.disableLighting();
    GlStateManager.scale(0.5F, 0.5F, 0.5F);
    GlStateManager.disableDepth();
    renderEnchantmentText(stack, x, y);
    GlStateManager.enableDepth();
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    GlStateManager.popMatrix();
  }
  
  private void renderEnchantmentText(ItemStack stack, int x, int y) {
    int enchantmentY = y - 24;
    if (stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
      (Minecraft.getMinecraft()).fontRendererObj.drawString("god", x * 2, enchantmentY, -43177);
      return;
    } 
    int color = -5592406;
    if (stack.getItem() instanceof net.minecraft.item.ItemArmor) {
      int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
      int projectileProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180308_g.effectId, stack);
      int blastProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
      int fireProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
      int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
      int featherFallingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180309_e.effectId, stack);
      int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
      if (protectionLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("pr" + protectionLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (unbreakingLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("un" + unbreakingLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (projectileProtectionLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("pp" + projectileProtectionLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (blastProtectionLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("bp" + blastProtectionLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (fireProtectionLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("fp" + fireProtectionLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (thornsLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("tho" + thornsLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (featherFallingLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("ff" + featherFallingLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (this.armor && 
        stack.getMaxDamage() - stack.getItemDamage() < stack.getMaxDamage()) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString((new StringBuilder(String.valueOf(stack.getMaxDamage() - stack.getItemDamage()))).toString(), x * 2, enchantmentY + 2, 39321);
        enchantmentY += 8;
      } 
    } 
    if (stack.getItem() instanceof net.minecraft.item.ItemBow) {
      int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
      int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
      int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
      if (powerLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("po" + powerLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (punchLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("pu" + punchLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (flameLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("fl" + flameLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
    } 
    if (stack.getItem() instanceof net.minecraft.item.ItemPickaxe) {
      int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
      int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
      if (efficiencyLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("ef" + efficiencyLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (fortuneLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("fo" + fortuneLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
    } 
    if (stack.getItem() instanceof net.minecraft.item.ItemAxe) {
      int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack);
      int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack);
      int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
      if (sharpnessLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("sh" + sharpnessLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (fireAspectLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("fa" + fireAspectLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (efficiencyLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("ef" + efficiencyLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
    } 
    if (stack.getItem() instanceof net.minecraft.item.ItemSword) {
      int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack);
      int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack);
      int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
      if (sharpnessLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("sh" + sharpnessLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (knockbackLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("kn" + knockbackLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
      if (fireAspectLevel > 0) {
        (Minecraft.getMinecraft()).fontRendererObj.drawString("fa" + fireAspectLevel, x * 2, enchantmentY, color);
        enchantmentY += 8;
      } 
    } 
    if (stack.getItem() == Items.golden_apple && stack.hasEffect())
      (Minecraft.getMinecraft()).fontRendererObj.func_175063_a("god", (x * 2), enchantmentY, -3977919); 
  }
  
  private String getDisplayName(EntityPlayer player) {
    String name = player.getDisplayName().getFormattedText();
    if (name.contains("KediTiwe"))
      name = "Enayi"; 
    String heartUnicode = " <3";
    if (name.contains(Minecraft.getMinecraft().getSession().getUsername()))
      name = "You"; 
    if (!this.health)
      return name; 
    float health = player.getHealth();
    EnumChatFormatting color = null;
    if (health > 18.0F) {
      color = EnumChatFormatting.GREEN;
    } else if (health > 16.0F) {
      color = EnumChatFormatting.DARK_GREEN;
    } else if (health > 12.0F) {
      color = EnumChatFormatting.YELLOW;
    } else if (health > 8.0F) {
      color = EnumChatFormatting.GOLD;
    } else if (health > 5.0F) {
      color = EnumChatFormatting.RED;
    } else {
      color = EnumChatFormatting.DARK_RED;
    } 
    if (Math.floor(health) == health) {
      name = String.valueOf(name) + color + " " + ((health > 0.0F) ? Integer.valueOf((int)Math.floor(health)) : "dead");
    } else {
      name = String.valueOf(name) + color + " " + ((health > 0.0F) ? Integer.valueOf((int)health) : "dead");
    } 
    return String.valueOf(name) + heartUnicode;
  }
  
  private int getDisplayColour(EntityPlayer player) {
    int colour = -5592406;
    if (player.isInvisible()) {
      colour = -1113785;
    } else if (player.isSneaking()) {
      colour = -6481515;
    } 
    return colour;
  }
  
  private double interpolate(double previous, double current, float delta) {
    return previous + (current - previous) * delta;
  }
}
