package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;

/**
 * GroundPopulator is responsible for creating the ground layer of the AFLR world.
 */
final class GroundPopulator {

	/**
	 * GroundPopulator has a private constructor, because it is a utility class.
	 */
	private GroundPopulator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Returns the block ID and legacy data value for a material in Minecraft 1.8–1.12.
	 * Maps original material names (e.g., "ANDESITE") to their block ID and data values (e.g., 1:5 for STONE).
	 * @param material The Bukkit Material.
	 * @param originalName The original material name from Config.yml (e.g., "ANDESITE", "STONE:5").
	 * @return An array of [blockId, dataValue].
	 */
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
	 * Creates the ground layer of the AFLR world.
	 *
	 * @param x the x position within the chunk
	 * @param y the y position within the chunk
	 * @param z the z position within the chunk
	 * @param cd the current ChunkData of the chunk
	 * @param random the randomizer of the world
	 * @return the new ChunkData of the chunk
	 */
	static ChunkData populate(int x, int y, int z, ChunkData cd, Random random) {
		World world = ((CraftWorld) cd.getClass().getMethod("getWorld").invoke(cd)).getHandle().getWorld();
		
		if (Options.worldGenerateWater && y < Options.worldHeight) {
			for (int newY = y; newY < y + 4; newY++) {
				int randomBlockType = random.nextInt(Options.worldWaterGroundMaterials.size());
				Material material = Options.worldWaterGroundMaterials.get(randomBlockType);
				String originalName = Options.worldWaterGroundMaterialNames.get(randomBlockType);
				int[] blockData = getBlockIdAndData(material, originalName);

				Util.setBlockFast(world, x, newY, z, blockData[0], (byte) blockData[1]);
			}
			y += 4;

			while (y <= Options.worldHeight + 2) {
				Util.setBlockFast(world, x, y, z, Material.WATER.getId(), (byte) 0);
				y++;
			}
		} else {
			for (int newY = y; newY < y + 3; newY++) {
				int randomBlockType = random.nextInt(Options.worldPreGroundMaterials.size());
				Material material = Options.worldPreGroundMaterials.get(randomBlockType);
				String originalName = Options.worldPreGroundMaterialNames.get(randomBlockType);
				int[] blockData = getBlockIdAndData(material, originalName);

				Util.setBlockFast(world, x, newY, z, blockData[0], (byte) blockData[1]);
			}
			y += 3;

			int randomBlockType = random.nextInt(Options.worldGroundMaterials.size());
			Material material = Options.worldGroundMaterials.get(randomBlockType);
			String originalName = Options.worldGroundMaterialNames.get(randomBlockType);
			int[] blockData = getBlockIdAndData(material, originalName);

			Util.setBlockFast(world, x, y, z, blockData[0], (byte) blockData[1]);
		}

		return cd;
	}

}
