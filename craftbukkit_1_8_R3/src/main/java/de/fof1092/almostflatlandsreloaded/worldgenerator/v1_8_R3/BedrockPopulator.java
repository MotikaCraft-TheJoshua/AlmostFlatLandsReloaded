package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import java.io.File;
import java.util.Random;

final class BedrockPopulator {
    private BedrockPopulator() {
        throw new IllegalStateException("Utility class");
    }

    static ChunkData populate(int x, int z, ChunkData cd, Random random) {
        File fileConfig = new File("plugins/AlmostFlatlandsReloaded/Config.yml");
        FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);
        boolean flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");
        int thickness = ymlFileConfig.getInt("FlatBedrock.Thickness");

        if (flatBedrockEnabled) {
            for (int y = Options.worldDepth; y < Options.worldDepth + thickness; y++) {
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
        }

        return cd;
    }
}
