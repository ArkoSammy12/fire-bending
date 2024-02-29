package xd.arkosammy.firebending.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LavaFireBlock extends CustomFireBlock{
    public LavaFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForPosition(BlockView world, BlockPos pos) {
        return this.getStateWithProperties(super.getStateForPosition(world, pos));
    }

    @Override
    public FireSource getFireSource() {
        return FireSource.LAVA;
    }

    @Override
    public boolean doTickFire(World world) {
        return world.getGameRules().getInt(this.getFireSource().getGameRule()) >= 0;
    }

    @Override
    public int getTickDelay(World world) {
        return doTickFire(world) ? 30 + world.getRandom().nextInt(world.getGameRules().getInt(this.getFireSource().getGameRule())) : 30 + world.getRandom().nextInt(10);
    }
}
