package com.github.Sheepbester;

import net.neoforged.bus.api.IEventBus;
import static com.github.Sheepbester.Convey.ITEMS;

public class Items {
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
