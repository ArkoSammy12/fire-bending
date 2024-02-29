package xd.arkosammy.firebending.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xd.arkosammy.firebending.FireBending;

public class FlintAndSteelFireBlock extends CustomFireBlock {

    public FlintAndSteelFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForPosition(BlockView world, BlockPos pos) {
        return this.getStateWithProperties(super.getStateForPosition(world, pos));
    }

    @Override
    public FireSource getFireSource() {
        return FireSource.FLINT_AND_STEEL;
    }

    @Override
    public boolean doTickFire(World world) {
        return world.getGameRules().getInt(this.getFireSource().getGameRule()) >= 0;
    }

    @Override
    public int getTickDelay(World world) {
        return doTickFire(world) ? world.getGameRules().getInt(this.getFireSource().getGameRule()) + world.getRandom().nextInt(10) : FireBending.DEFAULT_TICK_DELAY + world.getRandom().nextInt(10);
    }

}
