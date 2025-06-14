package de.fof1092.almostflatlandsreloaded.worldgenerator.v1_8_R3;

import java.util.Random;

import de.fof1092.almostflatlandsreloaded.Options;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;

/**
 * OrePopulator is responsible for creating the ores within the stone layer of the AFLR world.
 */
final class OrePopulator {

	/**
	 * OrePopulator has a private constructor, because it is a utility class.
	 */
	private OrePopulator() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the ores within the stone layer of the AFLR world.
	 *
	 * @param x the x position within the chunk
	 * @param y the y position within the chunk
	 * @param z the z position within the chunk
	 * @param cd the current ChunkData of the chunk
	 * @param random the randomizer of the world
	 * @return the new ChunkData of the chunk
	 */
	static ChunkData populate(int x, int y, int z, ChunkData cd, Random random) {
		for (int i = 0; i < Options.worldOres.size(); i++) {
			Material material = Options.worldOres.get(i);
			int chance = Options.worldOreChances.get(i);
			int maxHeight;
			switch (material.toString()) {
				case "COAL_ORE":
					maxHeight = 128;
					break;
				case "IRON_ORE":
					maxHeight = 64;
					break;
				case "GOLD_ORE":
				case "EMERALD_ORE":
					maxHeight = 32;
					break;
				case "REDSTONE_ORE":
				case "DIAMOND_ORE":
				case "LAPIS_ORE":
					maxHeight = 16;
					break;
				default:
					maxHeight = 64; // Default for custom materials
			}
			int attempts;
			switch (material.toString()) {
				case "COAL_ORE":
					attempts = 20 * 10;
					break;
				case "IRON_ORE":
					attempts = 20 * 6;
					break;
				case "GOLD_ORE":
					attempts = 1 * 4;
					break;
				case "EMERALD_ORE":
					attempts = 4 * 1;
					break;
				case "REDSTONE_ORE":
					attempts = 8 * 6;
					break;
				case "DIAMOND_ORE":
					attempts = 1 * 7;
					break;
				case "LAPIS_ORE":
					attempts = 1 * 5;
					break;
				default:
					attempts = 10; // Default for custom materials
			}
			for (int j = 0; j < attempts; j++) {
				setRandomBlock(x, y, z, cd, random, maxHeight, material, chance);
			}
		}

		return cd;
	}

	/**
	 * Creates the ores at a random height taking into account the maxHeight.
	 *
	 * @param x the x position within the chunk
	 * @param y the y position within the chunk
	 * @param z the z position within the chunk
	 * @param cd the current ChunkData of the chunk
	 * @param random the randomizer of the WorldGenerator
	 * @param maxHeight the maximum y of the block
	 * @param material the material of the block to be placed
	 * @param chance the spawn chance (1–100)
	 */
	private static void setRandomBlock(int x, int y, int z, ChunkData cd, Random random, int maxHeight, Material material, int chance) {
		int roll = random.nextInt(100) + 1;
		if (roll <= chance) {
			int rndX = random.nextInt(16);
			int rndZ = random.nextInt(16);
			int startY = Options.worldDepth + (Options.flatBedrockEnabled ? Options.flatBedrockThickness : 1);
			int rndY = random.nextInt((maxHeight - startY) - 4) + 4 + startY;

			if (rndX == x && rndZ == z) {
				if (rndY <= y) {
					cd.setBlock(x, rndY, z, material);
				}
			}
		}
	}
}
