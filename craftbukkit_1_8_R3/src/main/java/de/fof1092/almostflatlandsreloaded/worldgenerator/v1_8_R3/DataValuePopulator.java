package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

/**
 * DataValuePopulator applies legacy data values for blocks in Minecraft 1.8–1.12.
 */
final class DataValuePopulator extends BlockPopulator {

	/**
	 * Populates data values for blocks in the chunk.
	 */
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int realX = x + chunk.getX() * 16;
				int realZ = z + chunk.getZ() * 16;

				// Replicate height calculation from WorldGenerator
				SimplexOctaveGenerator wgen = new SimplexOctaveGenerator(world, 8);
				wgen.setScale(0.015625D);
				int height = (int) ((wgen.noise(realX, realZ, 0.5D, 0.5D) / 0.75) + Options.worldHeight);

				// Start above bedrock
				int startY = Options.worldDepth + (Options.flatBedrockEnabled ? Options.flatBedrockThickness : 1);

				// Process underground materials
				for (int y = startY; y < height; y++) {
					int randomBlockType = random.nextInt(Options.worldUndergroundMaterials.size());
					Material material = Options.worldUndergroundMaterials.get(randomBlockType);
					String originalName = Options.worldUndergroundMaterialNames.get(randomBlockType);

					if (material == Material.STONE) {
						byte data = getDataValue(originalName);
						if (data != 0) {
							Util.setBlockFast(world, realX, y, realZ, material.getId(), data);
						}
					}
				}

				// Process ores
				for (int i = 0; i < Options.worldOres.size(); i++) {
					Material material = Options.worldOres.get(i);
					String originalName = Options.worldOreNames.get(i);
					if (material == Material.STONE) {
						byte data = getDataValue(originalName);
						if (data != 0) {
							// Apply data value to ore positions (simplified, assumes ore placement logic)
							int chance = Options.worldOreChances.get(i);
							int maxHeight = getOreMaxHeight(material);
							int attempts = getOreAttempts(material);
							for (int j = 0; j < attempts; j++) {
								int roll = random.nextInt(100) + 1;
								if (roll <= chance) {
									int rndY = random.nextInt((maxHeight - startY) - 4) + 4 + startY;
									if (rndY <= height) {
										Util.setBlockFast(world, realX, rndY, realZ, material.getId(), data);
									}
								}
							}
						}
					}
				}

				// Process ground and pre-ground materials
				int y = height;
				if (Options.worldGenerateWater && y < Options.worldHeight) {
					for (int newY = y; newY < y + 4; newY++) {
						int randomBlockType = random.nextInt(Options.worldWaterGroundMaterials.size());
						Material material = Options.worldWaterGroundMaterials.get(randomBlockType);
						String originalName = Options.worldWaterGroundMaterialNames.get(randomBlockType);

						if (material == Material.STONE) {
							byte data = getDataValue(originalName);
							if (data != 0) {
								Util.setBlockFast(world, realX, newY, realZ, material.getId(), data);
							}
						}
					}
					y += 4;
				} else {
					for (int newY = y; newY < y + 3; newY++) {
						int randomBlockType = random.nextInt(Options.worldPreGroundMaterials.size());
						Material material = Options.worldPreGroundMaterials.get(randomBlockType);
						String originalName = Options.worldPreGroundMaterialNames.get(randomBlockType);

						if (material == Material.STONE) {
							byte data = getDataValue(originalName);
							if (data != 0) {
								Util.setBlockFast(world, realX, newY, realZ, material.getId(), data);
							}
						}
					}
					y += 3;

					int randomBlockType = random.nextInt(Options.worldGroundMaterials.size());
					Material material = Options.worldGroundMaterials.get(randomBlockType);
					String originalName = Options.worldGroundMaterialNames.get(randomBlockType);

					if (material == Material.GRASS || material == Material.STONE) {
						byte data = getDataValue(originalName);
						if (data != 0 || material == Material.GRASS) {
							Util.setBlockFast(world, realX, y, realZ, material.getId(), data);
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the legacy data value for a material name in Minecraft 1.8–1.12.
	 * @param originalName The original material name from Config.yml (e.g., "ANDESITE", "STONE:5").
	 * @return The data value (e.g., 5 for ANDESITE).
	 */
	@SuppressWarnings("deprecation")
	private static byte getDataValue(String originalName) {
		String materialName = originalName.contains(":") ? originalName.split(":")[0] : originalName;
		if (originalName.contains(":")) {
			try {
				return Byte.parseByte(originalName.split(":")[1]);
			} catch (NumberFormatException e) {
				// Fallback to mapping
			}
		}
		switch (materialName.toUpperCase()) {
			case "GRANITE":
				return 1;
			case "DIORITE":
				return 3;
			case "ANDESITE":
				return 5;
			case "GRASS_BLOCK":
				return 0; // GRASS
			default:
				return 0; // Default for STONE or other materials
		}
	}

	/**
	 * Returns the max height for an ore material.
	 */
	private int getOreMaxHeight(Material material) {
		switch (material.toString()) {
			case "COAL_ORE":
				return 128;
			case "IRON_ORE":
				return 64;
			case "GOLD_ORE":
			case "EMERALD_ORE":
				return 32;
			case "REDSTONE_ORE":
			case "DIAMOND_ORE":
			case "LAPIS_ORE":
				return 16;
			default:
				return 64;
		}
	}

	/**
	 * Returns the number of placement attempts for an ore material.
	 */
	private int getOreAttempts(Material material) {
		switch (material.toString()) {
			case "COAL_ORE":
				return 20 * 10;
			case "IRON_ORE":
				return 20 * 6;
			case "GOLD_ORE":
				return 1 * 4;
			case "EMERALD_ORE":
				return 4 * 1;
			case "REDSTONE_ORE":
				return 8 * 6;
			case "DIAMOND_ORE":
				return 1 * 7;
			case "LAPIS_ORE":
				return 1 * 5;
			default:
				return 10;
		}
	}
}
