package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import java.util.List;
import java.util.Random;

final class StonePopulator {
    private StonePopulator() {
        throw new IllegalStateException("Utility class");
    }

    static ChunkData populate(int x, int y, int z, ChunkData cd, Random random, List<String> worldUndergroundMaterialsNames) {
        int startY = Options.worldDepth + (Options.flatBedrockEnabled ? Options.flatBedrockThickness : 4);
        int randomBlockType = random.nextInt(worldUndergroundMaterialsNames.size());

        for (int newY = startY; newY < y; newY++) {
            cd.setBlock(x, newY, z, Options.worldUndergroundMaterials.get(randomBlockType));
        }

        return cd;
    }
}
