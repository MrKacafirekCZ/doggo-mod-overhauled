package net.lordmrk.dmo.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DogBed extends HorizontalFacingBlock {

    protected static final VoxelShape BOTTOM = Block.createCuboidShape(-4.0, 0.0, -4.0, 20.0, 8.0, 20.0);
    protected static final VoxelShape EMPTY = Block.createCuboidShape(-3.0, 4.0, -3.0, 19.0, 8.0, 19.0);
    protected static final VoxelShape EMPTY_EAST = Block.createCuboidShape(19.0, 4.0, 0.0, 20.0, 8.0, 16.0);
    protected static final VoxelShape EMPTY_NORTH = Block.createCuboidShape(0.0, 4.0, -4.0, 16.0, 8.0, -3.0);
    protected static final VoxelShape EMPTY_SOUTH = Block.createCuboidShape(0.0, 4.0, 19.0, 16.0, 8.0, 20.0);
    protected static final VoxelShape EMPTY_WEST = Block.createCuboidShape(-4.0, 4.0, 0.0, -3.0, 8.0, 16.0);

    protected static final VoxelShape SHAPE_EAST;
    protected static final VoxelShape SHAPE_NORTH;
    protected static final VoxelShape SHAPE_SOUTH;
    protected static final VoxelShape SHAPE_WEST;

    private final DyeColor color;
    private final WoodType woodType;

    public DogBed(DyeColor color, WoodType woodType) {
        super(FabricBlockSettings.create().sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY));

        this.color = color;
        this.woodType = woodType;

        this.setDefaultState(this.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    private void bounceEntity(Entity entity) {
        Vec3d vec = entity.getVelocity();

        if (vec.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec.x, -vec.y * 0.6600000262260437D * d, vec.z);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);

        switch(direction) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_EAST;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if(entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
            return;
        }

        this.bounceEntity(entity);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance * 0.5F);
    }

    static {
        SHAPE_EAST = VoxelShapes.combineAndSimplify(BOTTOM, VoxelShapes.union(EMPTY, EMPTY_EAST), BooleanBiFunction.ONLY_FIRST);
        SHAPE_NORTH = VoxelShapes.combineAndSimplify(BOTTOM, VoxelShapes.union(EMPTY, EMPTY_NORTH), BooleanBiFunction.ONLY_FIRST);
        SHAPE_SOUTH = VoxelShapes.combineAndSimplify(BOTTOM, VoxelShapes.union(EMPTY, EMPTY_SOUTH), BooleanBiFunction.ONLY_FIRST);
        SHAPE_WEST = VoxelShapes.combineAndSimplify(BOTTOM, VoxelShapes.union(EMPTY, EMPTY_WEST), BooleanBiFunction.ONLY_FIRST);
    }
}
