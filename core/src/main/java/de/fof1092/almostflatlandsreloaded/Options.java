package de.fof1092.almostflatlandsreloaded;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;

/**
 * Options is managing the adjustable parameters of the plugin.
 */
public final class Options {

	/**
	 * Manages the messages of the plugin.
	 */
	public static Map<String, String> msg = new HashMap<>();

	/**
	 * Determines at what altitude the AFLR world should be located.
	 */
	public static int worldHeight;

	/**
	 * Determines the depth of the AFLR world.
	 */
	public static int worldDepth;

	/**
	 * Determines which biome the AFLR world consists of.
	 */
	public static Biome worldBiome;

	/**
	 * Determines how much grass is generated in the AFLR world.
	 */
	public static int worldGrassChance;

	/**
	 * Determines how many flowers are generated in the AFLR world.
	 */
	public static int worldFlowerChance;

	/**
	 * Determines how many trees are generated in the AFLR world.
	 */
	public static int worldTreeChance;

	/**
	 * Determines which tree species are generated in the AFLR world.
	 */
	public static List<TreeType> worldTreeTypes = new ArrayList<>();

	/**
	 * Determines if water is being generated in the AFLR world.
	 */
	public static boolean worldGenerateWater;

	/**
	 * Determines whether a flat bedrock floor is generated.
	 */
	public static boolean flatBedrockEnabled;

	/**
	 * Determines the thickness of the flat bedrock floor (1–3).
	 */
	public static int flatBedrockThickness;

	/**
	 * Determines which materials are used as ores in the AFLR world.
	 */
	public static List<Material> worldOres = new ArrayList<>();

	/**
	 * Determines the spawn chances (1–100) for each ore.
	 */
	public static List<Integer> worldOreChances = new ArrayList<>();

	/**
	 * Stores the original ore material names from Config.yml.
	 */
	public static List<String> worldOreNames = new ArrayList<>();

	/**
	 * Determines which material is used in the underground in the AFLR world.
	 */
	public static List<Material> worldUndergroundMaterials = new ArrayList<>();
	public static List<String> worldUndergroundMaterialNames = new ArrayList<>();

	/**
	 * Determines which material is under the Ground Material in the AFLR world.
	 */
	public static List<Material> worldPreGroundMaterials = new ArrayList<>();
	public static List<String> worldPreGroundMaterialNames = new ArrayList<>();

	/**
	 * Determines which material is on the top level in the AFLR world.
	 */
	public static List<Material> worldGroundMaterials = new ArrayList<>();
	public static List<String> worldGroundMaterialNames = new ArrayList<>();

	/**
	 * Determines which material is under the water in the AFLR world.
	 */
	public static List<Material> worldWaterGroundMaterials = new ArrayList<>();
	public static List<String> worldWaterGroundMaterialNames = new ArrayList<>();

	/**
	 * Options has a private constructor, because it is a utility class.
	 */
	private Options() {
		throw new IllegalStateException("Utility class");
	}
}
