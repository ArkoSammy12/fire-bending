package xd.arkosammy.firebending.blocks;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import xd.arkosammy.firebending.FireBending;
import xd.arkosammy.firebending.util.ducks.FireBlockAccessor;

import java.util.function.Supplier;

public abstract class CustomFireBlock extends FireBlock implements PolymerBlock, PolymerClientDecoded {

    public CustomFireBlock(Settings settings) {
        super(settings);
    }

    public static BlockState getState(BlockView world, BlockPos pos, FireSource fireSource, Supplier<BlockState> originalFireStateProvider) {
        BlockState normalFireState = originalFireStateProvider.get();
        if(normalFireState.isOf(Blocks.SOUL_FIRE)){
            return normalFireState;
        }
        return switch (fireSource) {
            case LIGHTNING -> ((FireBlockAccessor) FireSource.LIGHTNING.getBlock()).fire_bending$getStateForPosition(world, pos);
            case FLINT_AND_STEEL -> ((FireBlockAccessor) FireSource.FLINT_AND_STEEL.getBlock()).fire_bending$getStateForPosition(world, pos);
            case LAVA -> ((FireBlockAccessor) FireSource.LAVA.getBlock()).fire_bending$getStateForPosition(world, pos);
        };
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return Blocks.FIRE;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.getPolymerBlock(state).getStateWithProperties(state);
    }

    public abstract FireSource getFireSource();

    public boolean doTickFire(World world) {
        return world.getGameRules().getInt(this.getFireSource().getGameRule()) >= 0;
    }

    public int getTickDelay(World world) {
        return doTickFire(world) ? world.getGameRules().getInt(this.getFireSource().getGameRule()) + world.getRandom().nextInt(10) : FireBending.DEFAULT_TICK_DELAY + world.getRandom().nextInt(10);
    }

    @Override
    public BlockState getStateForPosition(BlockView world, BlockPos pos) {
        return this.getStateWithProperties(super.getStateForPosition(world, pos));
    }

    @Override
    public BlockState getStateWithAge(WorldAccess world, BlockPos pos, int age) {
        BlockState state = getState(world, pos, this.getFireSource(), () -> AbstractFireBlock.getState(world, pos));
        if(!(state.getBlock() instanceof FireBlock)){
            return state;
        }
        return state.with(AGE, age);
    }

}
