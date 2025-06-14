package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.List;
import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.World;
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
	 * Returns the block ID and legacy data value for a material in Minecraft 1.8–1.12.
	 * Maps original material names (e.g., "ANDESITE") to their block ID and data values (e.g., 1:5 for STONE).
	 * @param material The Bukkit Material.
	 * @param originalName The original material name from Config.yml (e.g., "ANDESITE", "STONE:5").
	 * @return An array of [blockId, dataValue].
	 */
	@SuppressWarnings("deprecation")
	private static int[] getBlockIdAndData(Material material, String originalName) {
		int blockId = material.getId(); // Get NMS block ID
		byte data = 0;

		if (material == Material.STONE) {
			if (originalName.contains(":")) {
				try {
					data = Byte.parseByte(originalName.split(":")[1]);
				} catch (NumberFormatException e) {
					// Fallback to mapping
				}
			}
			if (data == 0) {
				switch (originalName.toUpperCase()) {
					case "GRANITE":
						data = 1;
						break;
					case "DIORITE":
						data = 3;
						break;
					case "ANDESITE":
						data = 5;
						break;
					default:
						data = 0; // Default STONE
				}
			}
		} else if (material == Material.GRASS) {
			data = 0; // GRASS_BLOCK
		}

		return new int[]{blockId, data};
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
	 * @param world the world being generated
	 * @return the new ChunkData of the chunk
	 */
	static ChunkData populate(int x, int y, int z, ChunkData cd, Random random, List<String> originalMaterials, World world) {
		for (int newY = Options.worldDepth + 1; newY < y; newY++) {
			int randomBlockType = random.nextInt(Options.worldUndergroundMaterials.size());
			Material material = Options.worldUndergroundMaterials.get(randomBlockType);
			String originalName = originalMaterials.get(randomBlockType);
			int[] blockData = getBlockIdAndData(material, originalName);

			Util.setBlockFast(world, x, newY, z, blockData[0], (byte) blockData[1]);
		}

		return cd;
	}
}
