package xd.arkosammy.firebending.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xd.arkosammy.firebending.blocks.CustomFireBlock;
import xd.arkosammy.firebending.blocks.FireSource;

// Target inner anonymous class of type FallibleItemDispenserBehavior that corresponds to the registration of the flint and steel item dispenser behavior
@Mixin(targets = "net.minecraft.block.dispenser.DispenserBehavior$10")
public abstract class DispenserBehavior$10Mixin {

    @WrapOperation(method = "dispenseSilently", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractFireBlock;getState(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private BlockState modifyFireSpawned(BlockView world, BlockPos pos, Operation<BlockState> original){
        return CustomFireBlock.getState(world, pos, FireSource.FLINT_AND_STEEL, () -> original.call(world, pos));
    }

}
