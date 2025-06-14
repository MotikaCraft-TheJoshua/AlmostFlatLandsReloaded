package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;

/**
 * BedrockPopulator is responsible for creating the bedrock layer of the AFLR world.
 */
final class BedrockPopulator {

	/**
	 * BedrockPopulator has a private constructor, because it is a utility class.
	 */
	private BedrockPopulator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the bedrock layer of the AFLR world.
	 *
	 * @param x the x position within the chunk
	 * @param z the z position within the chunk
	 * @param cd the current ChunkData of the chunk
	 * @param random the randomizer of the world
	 * @return the new ChunkData of the chunk
	 */
	static ChunkData populate(int x, int z, ChunkData cd, Random random) {
		if (Options.flatBedrockEnabled) {
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
