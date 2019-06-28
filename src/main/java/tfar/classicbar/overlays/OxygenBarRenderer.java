package tfar.classicbar.overlays;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.ForgeIngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import tfar.classicbar.Color;

import static tfar.classicbar.ColorUtils.hex2Color;
import static tfar.classicbar.config.ModConfig.*;
import static tfar.classicbar.ModUtils.*;

/*
    Class handles the drawing of the oxygen bar
 */

public class OxygenBarRenderer {
  private final Minecraft mc = Minecraft.getInstance();

  public OxygenBarRenderer() {
  }

  @SubscribeEvent(priority = EventPriority.LOW)
  public void renderOxygenBar(RenderGameOverlayEvent.Pre event) {

    Entity renderViewEntity = mc.getRenderViewEntity();
    if (event.getType() != RenderGameOverlayEvent.ElementType.AIR
            || event.isCanceled()
            || !(renderViewEntity instanceof PlayerEntity)) return;
    event.setCanceled(true);
    PlayerEntity player = (PlayerEntity) renderViewEntity;
    int air = player.getAir();
    if (air >= 300) return;
    int scaledWidth = mc.mainWindow.getScaledWidth();
    int scaledHeight = mc.mainWindow.getScaledHeight();
    //Push to avoid lasting changes

    int xStart = scaledWidth / 2 + 10;
    int yStart = scaledHeight - 49;
    if (general.overlays.displayToughnessBar && player.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getValue() >= 1)
      yStart -= 10;
    if (ModList.get().isLoaded("toughasnails")) yStart -= 10;

    mc.getProfiler().startSection("air");
    GlStateManager.pushMatrix();
    GlStateManager.enableBlend();

    //Bind our Custom bar
    mc.getTextureManager().bindTexture(ICON_BAR);
    //Bar background
    drawTexturedModalRect(xStart, yStart, 0, 0, 81, 9);

    //draw portion of bar based on air amount

    int f = xStart + 79 - getWidth(air, 300);
    hex2Color(colors.oxygenBarColor).color2Gl();
    drawTexturedModalRect(f, yStart + 1, 1, 10, getWidth(air, 300), 7);

    //draw air amount
    int h1 = (int) Math.floor(air / 20);

    int c = Integer.decode(colors.oxygenBarColor);
    int i3 = general.displayIcons ? 1 : 0;
    if (numbers.showPercent) h1 = air / 3;
    drawStringOnHUD(h1 + "", xStart + 9 * i3 + rightTextOffset, yStart - 1, c);
    //Reset back to normal settings
    Color.reset();
    mc.getTextureManager().bindTexture(ICON_VANILLA);
    ForgeIngameGui.left_height += 10;
    if (general.displayIcons) {
      //Draw air icon
      drawTexturedModalRect(xStart + 82, yStart, 16, 18, 9, 9);
    }
    GlStateManager.disableBlend();
    //Revert our state back
    GlStateManager.popMatrix();
    mc.getProfiler().endSection();
    event.setCanceled(true);
  }

}