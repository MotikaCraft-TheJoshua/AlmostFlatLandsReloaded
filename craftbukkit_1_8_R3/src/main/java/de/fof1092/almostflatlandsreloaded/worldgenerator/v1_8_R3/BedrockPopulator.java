package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import java.io.File;
import java.util.Random;

final class BedrockPopulator {

    private static long lastLogTime = 0;
    private static final long LOG_INTERVAL = 3000; // 3 seconds

    private BedrockPopulator() {
        throw new IllegalStateException("Utility class");
    }

    static ChunkData populate(int x, int z, ChunkData cd, Random random) {
        File fileConfig = new File("plugins/AlmostFlatlandsReloaded/Config.yml");
        FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);
        boolean flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");

        // Log only for first block in chunk (x=0, z=0) and if 3 seconds have passed
        if (x == 0 && z == 0 && System.currentTimeMillis() - lastLogTime >= LOG_INTERVAL) {
            Bukkit.getLogger().info("[AlmostFlatLandsReloaded] BedrockPopulator: flatBedrockEnabled=" + flatBedrockEnabled + ", Options.flatBedrockEnabled=" + Options.flatBedrockEnabled);
            lastLogTime = System.currentTimeMillis();
        }

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
            } else {
                cd.setBlock(x, Options.worldDepth + 1, z, Material.AIR);
            }

            if (randomBlockBedrock2 <= 60) {
                cd.setBlock(x, Options.worldDepth + 2, z, Material.BEDROCK);
            } else {
                cd.setBlock(x, Options.worldDepth + 2, z, Material.AIR);
            }

            if (randomBlockBedrock3 <= 40) {
                cd.setBlock(x, Options.worldDepth + 3, z, Material.BEDROCK);
            } else {
                cd.setBlock(x, Options.worldDepth + 3, z, Material.AIR);
            }

            // Log random values only once per chunk
            if (x == 0 && z == 0 && System.currentTimeMillis() - lastLogTime >= LOG_INTERVAL) {
                Bukkit.getLogger().info("[AlmostFlatLandsReloaded] BedrockPopulator: Random values: r1=" + randomBlockBedrock1 + ", r2=" + randomBlockBedrock2 + ", r3=" + randomBlockBedrock3);
            }
        }

        return cd;
    }
}
