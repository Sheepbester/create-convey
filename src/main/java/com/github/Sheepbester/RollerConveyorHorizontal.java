package com.github.Sheepbester;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RollerConveyorHorizontal extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape CASING_W = Block.box(0, 9, 0, 2, 15, 16);
    private static final VoxelShape CASING_E = Block.box(14, 9, 0, 16, 15, 16);
    private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 9, 16);

    private static final VoxelShape LEG_NW = Block.box(0, 0, 0, 2, 8, 2);
    private static final VoxelShape LEG_NE = Block.box(14, 0, 0, 16, 8, 2);
    private static final VoxelShape LEG_SW = Block.box(0, 0, 14, 2, 8, 16);
    private static final VoxelShape LEG_SE = Block.box(14, 0, 14, 16, 8, 16);

    private static final VoxelShape NORTH_SHAPE =
            Shapes.or(CASING_W, CASING_E, TOP, LEG_NW, LEG_NE, LEG_SW, LEG_SE);

    private static final VoxelShape EAST_SHAPE  = rotateY(NORTH_SHAPE);
    private static final VoxelShape SOUTH_SHAPE = rotateY(EAST_SHAPE);
    private static final VoxelShape WEST_SHAPE  = rotateY(SOUTH_SHAPE);

    public RollerConveyorHorizontal(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    /**
     * Rotates a shape 90 degrees clockwise around Y axis.
     */
    private static VoxelShape rotateY(VoxelShape shape) {
        VoxelShape result = Shapes.empty();

        for (var box : shape.toAabbs()) {
            result = Shapes.or(result,
                    Block.box(
                            16 - box.maxZ * 16,
                            box.minY * 16,
                            box.minX * 16,
                            16 - box.minZ * 16,
                            box.maxY * 16,
                            box.maxX * 16
                    )
            );
        }

        return result;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case EAST  -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST  -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return getShape(state, level, pos, CollisionContext.empty());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
