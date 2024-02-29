package xd.arkosammy.firebending;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xd.arkosammy.firebending.blocks.FireSource;
import xd.arkosammy.firebending.util.Config;

public class FireBending implements ModInitializer {

	public static final String MOD_ID = "fire-bending";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final int DEFAULT_TICK_DELAY = 30;
	public static final GameRules.Key<GameRules.BooleanRule> DO_TICK_FIRE_ONLY_WHEN_PLAYER_NEARBY = GameRuleRegistry.register("doTickFireOnlyWhenPlayerNearby", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(false));
	public static final GameRules.Key<GameRules.IntRule> TICK_FIRE_WHEN_PLAYER_NEARBY_DISTANCE = GameRuleRegistry.register("tickFireWhenPlayerNearbyDistance", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(30));

	@Override
	public void onInitialize() {
		FireSource.registerBlocks();
		ServerLifecycleEvents.SERVER_STARTING.register(server -> Config.getInstance().readConfig());
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> Config.getInstance().writeConfig());
		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {

			LiteralCommandNode<ServerCommandSource> parentNode = CommandManager
					.literal(MOD_ID)
					.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
					.build();
			LiteralCommandNode<ServerCommandSource> reloadConfigNode = CommandManager
					.literal("reload_config")
					.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
					.executes((ctx) -> {
						Text feedback = Config.getInstance().readConfig() ? Text.literal(MOD_ID + " config successfully reloaded!").formatted(Formatting.GREEN) : Text.literal(MOD_ID + " config failed to reload! Please check logs for more information.").formatted(Formatting.RED);
						ServerPlayerEntity playerSource = ctx.getSource().getPlayer();
						if(playerSource == null){
							FireBending.LOGGER.error(feedback.getString());
							return Command.SINGLE_SUCCESS;
						}
						playerSource.sendMessage(feedback);
						return Command.SINGLE_SUCCESS;
					})
					.build();
			dispatcher.getRoot().addChild(parentNode);
			parentNode.addChild(reloadConfigNode);
		}));
		LOGGER.info("Thanks to @baconbacon123 for the idea for this mod :)");
	}

}