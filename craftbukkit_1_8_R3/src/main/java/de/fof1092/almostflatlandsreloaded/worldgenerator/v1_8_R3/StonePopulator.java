package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import java.io.File;
import java.util.List;
import java.util.Random;

final class StonePopulator {
    private StonePopulator() {
        throw new IllegalStateException("Utility class");
    }

    static ChunkData populate(int x, int y, int z, ChunkData cd, Random random, List<String> worldUndergroundMaterialsNames) {
        File fileConfig = new File("plugins/AlmostFlatlandsReloaded/Config.yml");
        FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);
        boolean flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");
        int thickness = ymlFileConfig.getInt("FlatBedrock.Thickness");
        int startY = Options.worldDepth + (flatBedrockEnabled ? thickness : 4);
        int randomBlockType = random.nextInt(worldUndergroundMaterialsNames.size());

        for (int newY = startY; newY < y; newY++) {
            cd.setBlock(x, newY, z, Options.worldUndergroundMaterials.get(randomBlockType));
        }

        return cd;
    }
}
