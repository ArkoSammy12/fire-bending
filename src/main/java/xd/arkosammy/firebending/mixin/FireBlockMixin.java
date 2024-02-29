package xd.arkosammy.firebending.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import xd.arkosammy.firebending.blocks.CustomFireBlock;
import xd.arkosammy.firebending.blocks.FireSource;
import xd.arkosammy.firebending.util.ducks.FireBlockAccessor;

import java.util.Arrays;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin implements FireBlockAccessor {

    @Shadow protected abstract BlockState getStateForPosition(BlockView world, BlockPos pos);

    @Override
    public BlockState fire_bending$getStateForPosition(BlockView world, BlockPos pos){
        return this.getStateForPosition(world, pos);
    }

    @ModifyExpressionValue(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;getFireTickDelay(Lnet/minecraft/util/math/random/Random;)I"))
    private int modifyFireTickDelay(int original, @Local ServerWorld world) {
        int fireTickDelay = original;
        if(!(((FireBlock) (Object) this) instanceof CustomFireBlock customFireBlock)) {
            return fireTickDelay;
        }
        fireTickDelay = customFireBlock.getTickDelay(world);
        return fireTickDelay;
    }

    @ModifyExpressionValue(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean modifyTickCondition(boolean original, @Local ServerWorld world){
        boolean tickFire = original;
        if(!(((FireBlock) (Object) this) instanceof CustomFireBlock customFireBlock)) {
            return tickFire;
        }
        tickFire = customFireBlock.doTickFire(world);
        return tickFire;
    }

    @WrapOperation(method = "registerDefaultFlammables", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;registerFlammableBlock(Lnet/minecraft/block/Block;II)V"))
    private static void registerFlammables(FireBlock instance, Block block, int burnChance, int spreadChance, Operation<Void> original) {
        original.call(instance, block, burnChance, spreadChance);
        Arrays.stream(FireSource.values()).forEach(fireSource -> fireSource.getBlock().registerFlammableBlock(block, burnChance, spreadChance));
    }

}
