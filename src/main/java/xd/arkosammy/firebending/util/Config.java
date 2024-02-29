package xd.arkosammy.firebending.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import xd.arkosammy.firebending.FireBending;
import xd.arkosammy.firebending.blocks.FireSource;
import xd.arkosammy.firebending.util.ducks.FireBlockAccessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    private static Config instance = new Config();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("%s-config.json".formatted(FireBending.MOD_ID));
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final List<String> flammableBlocksBlacklist = new ArrayList<>();

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public boolean isIdInBlacklist(String id){
        return flammableBlocksBlacklist.contains(id);
    }

    public void writeConfig() {
        String json = GSON.toJson(getInstance());
        try(BufferedWriter bf = Files.newBufferedWriter(CONFIG_PATH)) {
            if(!Files.exists(CONFIG_PATH)) {
                Files.createFile(CONFIG_PATH);
            }
            bf.write(json);
        } catch (IOException e){
            FireBending.LOGGER.error("Error writing to config file " + CONFIG_PATH + ": " + e);
        }
    }

    public boolean readConfig() {
        if(Files.exists(CONFIG_PATH)) {
            try(BufferedReader br = Files.newBufferedReader(CONFIG_PATH)) {
                instance = GSON.fromJson(br, Config.class);
                reloadFireBurnAndSpreadChances();
                return true;
            } catch (IOException e){
                FireBending.LOGGER.error("Error reading from config file " + CONFIG_PATH + ": " + e);
            }
        } else {
            FireBending.LOGGER.warn("Config file not found. Creating new config file at " + CONFIG_PATH + "...");
            getInstance().writeConfig();
        }
        return false;
    }

    private static void reloadFireBurnAndSpreadChances() {
        ((FireBlockAccessor) Blocks.FIRE).fire_bending$clearBurnAndSpreadChances();
        Arrays.stream(FireSource.values()).forEach(fireSource -> ((FireBlockAccessor) fireSource.getBlock()).fire_bending$clearBurnAndSpreadChances());
        FireBlock.registerDefaultFlammables();
    }

}
