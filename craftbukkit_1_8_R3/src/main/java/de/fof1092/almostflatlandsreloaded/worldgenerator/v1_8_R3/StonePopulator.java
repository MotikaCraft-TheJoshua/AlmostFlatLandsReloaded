package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import java.util.List;
import java.util.Random;

/**
 * StonePopulator is responsible for creating the stone layer of the AFLR world.
 */
final class StonePopulator {

	/**
	 * StonePopulator has a private constructor, because it is a utility class.
	 */
	private StonePopulator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the stone layer of the AFLR world.
	 *
	 * @param x the x position within the chunk
	 * @param y the y position within the chunk
	 * @param z the z position within the chunk
	 * @param cd the current ChunkData of the chunk
	 * @param random the randomizer of the world
	 * @param originalMaterials the original material names from Config.yml
	 * @return the new ChunkData of the chunk
	 */
	static ChunkData populate(int x, int y, int z, ChunkData cd, Random random, List<String> worldUndergroundMaterialsNames) {
		int startY = Options.worldDepth + (Options.flatBedrockEnabled ? Options.flatBedrockThickness : 1);
		Bukkit.getLogger().info("[AlmostFlatLandsReloaded] StonePopulator: x=" + x + ", z=" + z + ", startY=" + startY + ", flatBedrockEnabled=" + Options.flatBedrockEnabled);
		int randomBlockType = random.nextInt(worldUndergroundMaterialsNames.size());

		for (int newY = startY; newY < y; newY++) {
			cd.setBlock(x, newY, z, Options.worldUndergroundMaterials.get(randomBlockType));
			Bukkit.getLogger().info("[AlmostFlatLandsReloaded] StonePopulator: Set block at y=" + newY + ", material=" + Options.worldUndergroundMaterials.get(randomBlockType));
		}

		return cd;
	}
}
