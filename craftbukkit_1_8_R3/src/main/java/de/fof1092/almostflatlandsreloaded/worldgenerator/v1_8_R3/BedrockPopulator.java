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
        File fileConfig = new File("plugins/AlmostFlatlandsReloaded/Config.yml");
        FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);
        boolean flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");
        ServerLog.log("BedrockPopulator: x=" + x + ", z=" + z + ", flatBedrockEnabled=" + flatBedrockEnabled + ", Options.flatBedrockEnabled=" + Options.flatBedrockEnabled);

        if (flatBedrockEnabled) {
            ServerLog.log("BedrockPopulator: Generating flat bedrock, thickness=" + Options.flatBedrockThickness);
            for (int y = Options.worldDepth; y < Options.worldDepth + Options.flatBedrockThickness; y++) {
                cd.setBlock(x, y, z, Material.BEDROCK);
                ServerLog.log("BedrockPopulator: Set bedrock at y=" + y);
            }
        } else {
            ServerLog.log("BedrockPopulator: Generating randomized bedrock");
            int randomBlockBedrock1 = random.nextInt(100) + 1;
            int randomBlockBedrock2 = random.nextInt(100) + 1;
            int randomBlockBedrock3 = random.nextInt(100) + 1;
            ServerLog.log("BedrockPopulator: Random values: r1=" + randomBlockBedrock1 + ", r2=" + randomBlockBedrock2 + ", r3=" + randomBlockBedrock3);

            cd.setBlock(x, Options.worldDepth, z, Material.BEDROCK);
            ServerLog.log("BedrockPopulator: Set bedrock at y=" + Options.worldDepth);

            if (randomBlockBedrock1 <= 80) {
                cd.setBlock(x, Options.worldDepth + 1, z, Material.BEDROCK);
                ServerLog.log("BedrockPopulator: Set bedrock at y=" + (Options.worldDepth + 1));
            } else {
                ServerLog.log("BedrockPopulator: Skipped bedrock at y=" + (Options.worldDepth + 1));
            }

            if (randomBlockBedrock2 <= 60) {
                cd.setBlock(x, Options.worldDepth + 2, z, Material.BEDROCK);
                ServerLog.log("BedrockPopulator: Set bedrock at y=" + (Options.worldDepth + 2));
            } else {
                ServerLog.log("BedrockPopulator: Skipped bedrock at y=" + (Options.worldDepth + 2));
            }

            if (randomBlockBedrock3 <= 40) {
                cd.setBlock(x, Options.worldDepth + 3, z, Material.BEDROCK);
                ServerLog.log("BedrockPopulator: Set bedrock at y=" + (Options.worldDepth + 3));
            } else {
                ServerLog.log("BedrockPopulator: Skipped bedrock at y=" + (Options.worldDepth + 3));
            }
        }

        return cd;
    }
}
