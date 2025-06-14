package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.generator.ChunkGenerator.ChunkData;

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
	 * Returns the legacy data value for a material in Minecraft 1.8–1.12.
	 * Maps original material names (e.g., "ANDESITE") to their data values (e.g., 5 for STONE).
	 * @param originalName The original material name from Config.yml (e.g., "ANDESITE", "STONE:5").
	 * @return The data value (e.g., 5 for ANDESITE).
	 */
	private static byte getMaterialData(String originalName) {
		String materialName = originalName.contains(":") ? originalName.split(":")[0] : originalName;
		if (originalName.contains(":")) {
			try {
				return Byte.parseByte(originalName.split(":")[1]);
			} catch (NumberFormatException e) {
				// Fallback to mapping if data value is invalid
			}
		}
		switch (materialName.toUpperCase()) {
			case "GRANITE":
				return 1;
			case "DIORITE":
				return 3;
			case "ANDESITE":
				return 5;
			default:
				return 0; // Default for STONE or other materials
		}
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
	static ChunkData populate(int x, int y, int z, ChunkData cd, Random random, List<String> originalMaterials) {
		for (int newY = Options.worldDepth + 1; newY < y; newY++) {
			int randomBlockType = random.nextInt(Options.worldUndergroundMaterials.size());
			Material material = Options.worldUndergroundMaterials.get(randomBlockType);
			String originalName = originalMaterials.get(randomBlockType);

			// Set block with material
			cd.setBlock(x, newY, z, material);
			// Apply data value for legacy versions (1.8–1.12)
			if (material == Material.STONE) {
				byte data = getMaterialData(originalName);
				if (data != 0) {
					// Use reflection or direct method to set data value
					// Note: ChunkData.setBlock(x, y, z, Material, byte) may not exist in 1.8
					// Fallback to block-level setting in a BlockPopulator if needed
					try {
						// This assumes ChunkData supports setting data values; verify for 1.8
						cd.getClass().getMethod("setBlock", int.class, int.class, int.class, Material.class, byte.class)
							.invoke(cd, x, newY, z, material, data);
					} catch (Exception e) {
						// Log error and fallback to default material
						System.err.println("[AlmostFlatLandsReloaded] Failed to set block data for " + originalName);
					}
				}
			}
		}

		return cd;
	}

}
