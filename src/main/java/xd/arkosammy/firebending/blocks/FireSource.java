package xd.arkosammy.firebending.blocks;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import xd.arkosammy.firebending.FireBending;

import java.util.Arrays;

public enum FireSource {

    LIGHTNING("lightning_fire_block", new LightningFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE)), GameRuleRegistry.register("lightningFireTickDelay", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(FireBending.DEFAULT_TICK_DELAY, -1))),
    LAVA("lava_fire_block", new LavaFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE)), GameRuleRegistry.register("lavaFireTickDelay", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(FireBending.DEFAULT_TICK_DELAY, -1))),
    FLINT_AND_STEEL("flint_and_steel_fire_block", new FlintAndSteelFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE)), GameRuleRegistry.register("flintAndSteelFireTickDelay", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(FireBending.DEFAULT_TICK_DELAY, -1))),
    EXPLOSION("explosion_fire_block", new ExplosionFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE)), GameRuleRegistry.register("explosionFireTickDelay", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(FireBending.DEFAULT_TICK_DELAY, -1))),
    FIRE_CHARGE("fire_charge_fire_block", new FireChargeFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE)), GameRuleRegistry.register("fireChargeFireTickDelay", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(FireBending.DEFAULT_TICK_DELAY, -1)));

    private final FireBlock block;
    private final Identifier id;
    private final GameRules.Key<GameRules.IntRule> gameRule;

    public FireBlock getBlock(){
        return this.block;
    }

    Identifier getId() {
        return this.id;
    }

    GameRules.Key<GameRules.IntRule> getGameRule() {
        return this.gameRule;
    }

    FireSource(String path, FireBlock block, GameRules.Key<GameRules.IntRule> gameRule){
        this.block = block;
        this.id = new Identifier(FireBending.MOD_ID, path);
        this.gameRule = gameRule;
    }

    public static void registerBlocks() {
        Arrays.stream(FireSource.values()).forEach(fireSource -> Registry.register(Registries.BLOCK, fireSource.getId(), fireSource.getBlock()));
    }

}
