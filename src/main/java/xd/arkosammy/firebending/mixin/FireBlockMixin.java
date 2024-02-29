package xd.arkosammy.firebending.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import xd.arkosammy.firebending.FireBending;
import xd.arkosammy.firebending.blocks.CustomFireBlock;
import xd.arkosammy.firebending.blocks.FireSource;
import xd.arkosammy.firebending.util.Config;
import xd.arkosammy.firebending.util.ducks.FireBlockAccessor;

import java.util.Arrays;

@Debug(export = true)
@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends AbstractFireBlock implements FireBlockAccessor {

    public FireBlockMixin(Settings settings, float damage) {
        super(settings, damage);
    }

    @Shadow protected abstract BlockState getStateForPosition(BlockView world, BlockPos pos);

    @Shadow @Final private Object2IntMap<Block> burnChances;

    @Shadow @Final private Object2IntMap<Block> spreadChances;

    @Override
    public BlockState fire_bending$getStateForPosition(BlockView world, BlockPos pos){
        return this.getStateForPosition(world, pos);
    }

    @ModifyExpressionValue(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;getFireTickDelay(Lnet/minecraft/util/math/random/Random;)I"))
    private int modifyFireTickDelay(int original, @Local(argsOnly = true) ServerWorld world) {
        if(!(((FireBlock) (Object) this) instanceof CustomFireBlock customFireBlock)) {
            return original;
        }
        return customFireBlock.getTickDelay(world);
    }

    @ModifyExpressionValue(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean modifyTickCondition(boolean original, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) BlockPos pos){
        if(!(((FireBlock) (Object) this) instanceof CustomFireBlock customFireBlock)) {
            return original;
        }
        if(world.getGameRules().getBoolean(FireBending.DO_TICK_FIRE_ONLY_WHEN_PLAYER_NEARBY)) {
            return isPlayerWithinDistanceOfFire(pos, world);
        }
        return customFireBlock.doTickFire(world);
    }

    @WrapOperation(method = "registerDefaultFlammables", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;registerFlammableBlock(Lnet/minecraft/block/Block;II)V"))
    private static void registerFlammables(FireBlock instance, Block block, int burnChance, int spreadChance, Operation<Void> original) {
        if(!Config.getInstance().isIdInBlacklist(Registries.BLOCK.getId(block).toString())) {
            original.call(instance, block, burnChance, spreadChance);
            Arrays.stream(FireSource.values()).forEach(fireSource -> original.call(fireSource.getBlock(), block, burnChance, spreadChance));
        }
    }

    @Override
    public void fire_bending$clearBurnAndSpreadChances() {
        this.burnChances.clear();
        this.spreadChances.clear();
    }

    @Unique
    private static boolean isPlayerWithinDistanceOfFire(BlockPos fireBlockPos, ServerWorld world) {
        int distance = world.getGameRules().getInt(FireBending.TICK_FIRE_WHEN_PLAYER_NEARBY_DISTANCE);
        int radiusSq = distance * distance;
        return world.getPlayers().stream().anyMatch((player) -> player.squaredDistanceTo(fireBlockPos.getX(), fireBlockPos.getY(), fireBlockPos.getZ()) <= radiusSq);
    }

}
