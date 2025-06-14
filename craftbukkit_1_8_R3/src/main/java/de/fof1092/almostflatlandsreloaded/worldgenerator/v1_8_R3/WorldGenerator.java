package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * WorldGenerator manages the AFLR world generator for Minecraft 1.8.
 */
public class WorldGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        ChunkData cd = createChunkData(world);
        SimplexOctaveGenerator wgen = new SimplexOctaveGenerator(world, 8);
        wgen.setScale(0.015625D);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int realX = x + chunkX * 16;
                int realZ = z + chunkZ * 16;
                double normalHeight = (wgen.noise(realX, realZ, 0.5D, 0.5D) / 0.75) + Options.worldHeight;

                cd = BedrockPopulator.populate(x, z, cd, random);
                cd = StonePopulator.populate(x, (int) normalHeight, z, cd, random, Options.worldUndergroundMaterialNames);
                if (!Options.worldOres.isEmpty()) {
                    cd = OrePopulator.populate(x, (int) normalHeight, z, cd, random);
                }
                cd = GroundPopulator.populate(x, (int) normalHeight, z, cd, random);
                biomeGrid.setBiome(x, z, Options.worldBiome);
            }
        }

        return cd;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> populators = new ArrayList<>();
        populators.add(new DataValuePopulator());
        populators.add(new FlowerAndGrassPopulator());
        populators.add(new TreePopulator());
        return populators;
    }
}
