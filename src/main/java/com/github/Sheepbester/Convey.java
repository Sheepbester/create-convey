package com.github.Sheepbester;

import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Convey.MODID)
public class Convey {
    // Define mod id in a common place for everything to reference
    public static final String                              MODID = "convey";
    public static final Logger                              LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks             BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items              ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab>   CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public Convey(IEventBus modEventBus, ModContainer modContainer) {
        Blocks.register(modEventBus);
        Items.register(modEventBus);
        CreativeModeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if (event.getTab() == CreativeModeTabs.CREATE_CONVEY.get()) {

            event.accept(Blocks.ANDESITE_ROLLER_CONVEYOR_HORIZONTAL.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
