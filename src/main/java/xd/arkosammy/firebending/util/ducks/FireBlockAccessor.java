package xd.arkosammy.firebending.util.ducks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface FireBlockAccessor {

    BlockState fire_bending$getStateForPosition(BlockView world, BlockPos pos);

    void fire_bending$clearBurnAndSpreadChances();

}
