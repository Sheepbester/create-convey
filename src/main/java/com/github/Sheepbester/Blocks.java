package com.github.Sheepbester;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

import static com.github.Sheepbester.Convey.BLOCKS;

public class Blocks {

    private static <T extends Block> DeferredBlock<T> registerBlock(
            String name,
            Supplier<T> block
    ) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(name, block);

        // Automatically register BlockItem
        Convey.ITEMS.register(name,
                () -> new BlockItem(registeredBlock.get(), new Item.Properties()));

        return registeredBlock;
    }

    public static final DeferredBlock<Block> ANDESITE_ROLLER_CONVEYOR_HORIZONTAL =
            registerBlock("andesite_roller_conveyor_horizontal",
                    () -> new RollerConveyorHorizontal(
                            BlockBehaviour.Properties.of()
                            .destroyTime(2.0f)
                            .sound(SoundType.WOOD)
                    ));
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
