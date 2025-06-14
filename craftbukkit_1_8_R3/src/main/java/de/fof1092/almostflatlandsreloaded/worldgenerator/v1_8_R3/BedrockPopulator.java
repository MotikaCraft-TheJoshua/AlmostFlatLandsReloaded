package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import de.fof1092.almostflatlandsreloaded.pluginmanager.ServerLog;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import java.io.File;
import java.util.Random;

/**
 * BedrockPopulator is responsible for creating the bedrock layer of the AFLR world.
 */
final class BedrockPopulator {

    private BedrockPopulator() {
        throw new IllegalStateException("Utility class");
    }

    static ChunkData populate(int x, int z, ChunkData cd, Random random) {
        // Force reread config to ensure correct value
        File fileConfig = new File("plugins/AlmostFlatlandsReloaded/Config.yml");
        FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);
        boolean flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");
        ServerLog.log("BedrockPopulator: flatBedrockEnabled = " + flatBedrockEnabled + ", Options.flatBedrockEnabled = " + Options.flatBedrockEnabled);

        if (flatBedrockEnabled) {
            for (int y = Options.worldDepth; y < Options.worldDepth + Options.flatBedrockThickness; y++) {
                cd.setBlock(x, y, z, Material.BEDROCK);
            }
        } else {
            int randomBlockBedrock1 = random.nextInt(100) + 1;
            int randomBlockBedrock2 = random.nextInt(100) + 1;
            int randomBlockBedrock3 = random.nextInt(100) + 1;

            cd.setBlock(x, Options.worldDepth, z, Material.BEDROCK);

            if (randomBlockBedrock1 <= 80) {
                cd.setBlock(x, Options.worldDepth + 1, z, Material.BEDROCK);
            }

            if (randomBlockBedrock2 <= 60) {
                cd.setBlock(x, Options.worldDepth + 2, z, Material.BEDROCK);
            }

            if (randomBlockBedrock3 <= 40) {
                cd.setBlock(x, Options.worldDepth + 3, z, Material.BEDROCK);
            }
        }

        return cd;
    }
}
