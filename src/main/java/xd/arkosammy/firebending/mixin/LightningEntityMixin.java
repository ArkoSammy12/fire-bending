package xd.arkosammy.firebending.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xd.arkosammy.firebending.blocks.CustomFireBlock;
import xd.arkosammy.firebending.blocks.FireSource;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {

    @WrapOperation(method = "spawnFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractFireBlock;getState(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
    private BlockState modifyFireSpawned(BlockView world, BlockPos pos, Operation<BlockState> original){
        return CustomFireBlock.getState(world, pos, FireSource.LIGHTNING, () -> original.call(world, pos));
    }

}
