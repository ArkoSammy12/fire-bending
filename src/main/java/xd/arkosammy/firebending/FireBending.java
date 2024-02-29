package xd.arkosammy.firebending;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xd.arkosammy.firebending.blocks.FireSource;

public class FireBending implements DedicatedServerModInitializer {

	public static final String MOD_ID = "fire-bending";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final int DEFAULT_TICK_DELAY = 30;

	@Override
	public void onInitializeServer() {
		FireSource.registerBlocks();
	}


}