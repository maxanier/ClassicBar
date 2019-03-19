package tfar.classicbar.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import tfar.classicbar.overlays.*;
import tfar.classicbar.overlays.modoverlays.LavaCharmRenderer;

import static tfar.classicbar.ModConfig.*;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        ConfigEventHandler configEventHandler = new ConfigEventHandler();
        MinecraftForge.EVENT_BUS.register(configEventHandler);

        //Register renderers for events

        MinecraftForge.EVENT_BUS.register(new HealthBarRenderer());
        MinecraftForge.EVENT_BUS.register(new HealthBarMountRenderer());
        MinecraftForge.EVENT_BUS.register(new ArmorBarRenderer());
        if (displayToughnessBar)MinecraftForge.EVENT_BUS.register(new ArmorToughnessBarRenderer());
        MinecraftForge.EVENT_BUS.register(new OxygenBarRenderer());
        MinecraftForge.EVENT_BUS.register(new HungerBarRenderer());

        //mod renderers
        if (Loader.isModLoaded("randomthings"))
            MinecraftForge.EVENT_BUS.register(new LavaCharmRenderer());
        if (Loader.isModLoaded("superiorshields")){}
           // MinecraftForge.EVENT_BUS.register(new SuperiorShieldRenderer());
    }



}
