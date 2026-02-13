package com.github.Sheepbester;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.github.Sheepbester.Convey.CREATIVE_MODE_TABS;

public class CreativeModeTabs {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_CONVEY =
            CREATIVE_MODE_TABS.register("create_convey",
                    () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.convey"))
                        .icon(() ->Blocks.ANDESITE_ROLLER_CONVEYOR_HORIZONTAL
                            .get()
                            .asItem()
                            .getDefaultInstance())
                        .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
