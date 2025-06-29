package de.fof1092.almostflatlandsreloaded;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.fof1092.almostflatlandsreloaded.pluginmanager.Command;
import de.fof1092.almostflatlandsreloaded.pluginmanager.CommandListener;
import de.fof1092.almostflatlandsreloaded.pluginmanager.ServerLog;
import de.fof1092.almostflatlandsreloaded.pluginmanager.VersionManager;
import de.fof1092.almostflatlandsreloaded.pluginmanager.VersionManager.BukkitVersion;
import de.fof1092.almostflatlandsreloaded.pluginmanager.Spigot.HelpPageListener;
import de.fof1092.almostflatlandsreloaded.pluginmanager.Spigot.UpdateListener;

/**
 * Main is the main class of the plugin.
 */
public class AlmostFlatlandsReloaded extends JavaPlugin {

	/**
	 * Provides the Main class to the getPlugin() method.
	 */
	private static AlmostFlatlandsReloaded plugin;

	/**
	 * Provides the Main class to the plugin.
	 *
	 * @return the Main class
	 */
	public static AlmostFlatlandsReloaded getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		System.out.println("[AlmostFlatLandsReloaded] a Plugin by F_o_F_1092");

		plugin = this;

		ServerLog.setPluginTag("§2[§a§lAlmostFlatLandsReloaded§2]§a");
		UpdateListener.initializeUpdateListener(1.32, "1.3.2", 55405);
		UpdateListener.checkForUpdate();

		setup();

		this.getCommand("AlmostFlatLandsReloaded").setExecutor(new CommnandAlmostFlatLandsReloaded());
		this.getCommand("AlmostFlatLandsReloaded").setTabCompleter(new CommnandAlmostFlatLandsReloadedTabCompleter());

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventListener(), this);
	}

	/**
	 * Provides the logic for the setup of the plugin.
	 */
	public static void setup() {
		String[] packageArgs = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (packageArgs.length >= 4) {
			VersionManager.setVersionManager(packageArgs[3], VersionManager.ServerType.BUKKIT, false);
		} else {
			// Problem in 1.20.6
			VersionManager.setVersionManager("UNKNOWN", VersionManager.ServerType.BUKKIT, false);
		}

		File fileConfig = new File("plugins/AlmostFlatLandsReloaded/Config.yml");
		FileConfiguration ymlFileConfig = YamlConfiguration.loadConfiguration(fileConfig);

		if (!fileConfig.exists()) {
			try {
				ymlFileConfig.set("Version", UpdateListener.getUpdateDoubleVersion());
				ymlFileConfig.set("GameVersion.SetOwn", false);
				ymlFileConfig.set("GameVersion.Version", "v1_20_R1");
				ymlFileConfig.set("ColoredConsoleText", true);
				ymlFileConfig.set("ShowUpdateMessage", true);

				ymlFileConfig.set("World.Height", 32);
				if (VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R2 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_10_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_11_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_12_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_13_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_13_R2 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_14_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_15_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R2 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R3 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_17_R1) {
					ymlFileConfig.set("World.Depth", 0);
				} else {
					ymlFileConfig.set("World.Depth", -64);
				}

				ymlFileConfig.set("World.Biome", Biome.PLAINS.toString());
				ymlFileConfig.set("World.GrassChance", 14);
				ymlFileConfig.set("World.FlowerChance", 1);
				ymlFileConfig.set("World.TreeChance", 15);

				List<String> treeTypes = new ArrayList<>();
				treeTypes.add(TreeType.TREE.toString());
				treeTypes.add(TreeType.BIRCH.toString());
				treeTypes.add(TreeType.BIG_TREE.toString());
				ymlFileConfig.set("World.TreeTypes", treeTypes);

				ymlFileConfig.set("World.GenerateWater", false);

				List<String> ores = new ArrayList<>();
				ores.add("COAL_ORE:128:20");
				ores.add("IRON_ORE:64:12");
				ores.add("GOLD_ORE:32:4");
				ores.add("EMERALD_ORE:32:4");
				ores.add("REDSTONE_ORE:16:48");
				ores.add("DIAMOND_ORE:16:7");
				ores.add("LAPIS_ORE:16:5");
				ymlFileConfig.set("World.Ores", ores);

				ymlFileConfig.set("FlatBedrock.Enabled", false);
				ymlFileConfig.set("FlatBedrock.Thickness", 1);

				List<String> undergroundMaterials = new ArrayList<>();
				if (VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R2 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_10_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_11_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_12_R1) {
					undergroundMaterials.add("STONE");
					undergroundMaterials.add("STONE");
					undergroundMaterials.add("ANDESITE");
				} else {
					undergroundMaterials.add(Material.STONE.toString());
					undergroundMaterials.add(Material.STONE.toString());
					undergroundMaterials.add(Material.ANDESITE.toString());
				}
				ymlFileConfig.set("World.UndergroundMaterials", undergroundMaterials);

				List<String> preGroundMaterials = new ArrayList<>();
				preGroundMaterials.add(Material.DIRT.toString());
				ymlFileConfig.set("World.PreGroundMaterials", preGroundMaterials);

				List<String> groundMaterials = new ArrayList<>();
				if (VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R2 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_10_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_11_R1 ||
						VersionManager.getBukkitVersion() == BukkitVersion.v1_12_R1) {
					groundMaterials.add("GRASS");
				} else {
					groundMaterials.add(Material.GRASS_BLOCK.toString());
				}
				ymlFileConfig.set("World.GroundMaterials", groundMaterials);

				List<String> waterGroundMaterials = new ArrayList<>();
				waterGroundMaterials.add(Material.SAND.toString());
				ymlFileConfig.set("World.WaterGroundMaterials", waterGroundMaterials);

				ymlFileConfig.save(fileConfig);
			} catch (IOException e) {
				ServerLog.err("Can't create the Config.yml. [" + e.getMessage() + "]");
			}
		} else {
			double version = ymlFileConfig.getDouble("Version");
			if (version < UpdateListener.getUpdateDoubleVersion()) {
				try {
					if (version < 1.22) {
						ymlFileConfig.set("World.Height", ymlFileConfig.getInt("World.Hight"));
						ymlFileConfig.set("World.Hight", null);
					}
					if (version < 1.31) {
						ymlFileConfig.set("World.Depth", 0);
						ymlFileConfig.set("World.GenerateWater", false);
						ymlFileConfig.set("World.WaterHeight", null);
					}
					// Migrate worldOresChance to new ores list if present
					if (ymlFileConfig.contains("World.OresChance")) {
						int oresChance = ymlFileConfig.getInt("World.OresChance");
						List<String> ores = new ArrayList<>();
						ores.add("COAL_ORE:128:" + (oresChance * 20 / 100));
						ores.add("IRON_ORE:64:" + (oresChance * 12 / 100));
						ores.add("GOLD_ORE:32:" + (oresChance * 4 / 100));
						ores.add("EMERALD_ORE:32:" + (oresChance * 4 / 100));
						ores.add("REDSTONE_ORE:16:" + (oresChance * 48 / 100));
						ores.add("DIAMOND_ORE:16:" + (oresChance * 7 / 100));
						ores.add("LAPIS_ORE:16:" + (oresChance * 5 / 100));
						ymlFileConfig.set("World.Ores", ores);
						ymlFileConfig.set("World.OresChance", null);
					}
					ymlFileConfig.set("Version", UpdateListener.getUpdateDoubleVersion());
					ymlFileConfig.save(fileConfig);
				} catch (IOException e) {
					ServerLog.err("Can't update the Config.yml. [" + e.getMessage() + "]");
				}
			}
		}

		ServerLog.setUseColoredColores(ymlFileConfig.getBoolean("ColoredConsoleText"));
		UpdateListener.showUpdateMessage = ymlFileConfig.getBoolean("ShowUpdateMessage");

		if (!ymlFileConfig.getBoolean("GameVersion.SetOwn")) {
			ServerLog.log("ServerType:§2 " + VersionManager.getSetverTypeString() + "§a, Version:§2 " + VersionManager.getBukkitVersion());
		} else {
			VersionManager.setVersionManager(ymlFileConfig.getString("GameVersion.Version"), VersionManager.ServerType.BUKKIT, true);
			ServerLog.log("ServerType:§2 " + VersionManager.getSetverTypeString() + "§a, Version:§2 " + VersionManager.getBukkitVersion() + "§a | §2(Self configurated)");
		}

		Options.worldHeight = ymlFileConfig.getInt("World.Height");
		Options.worldDepth = ymlFileConfig.getInt("World.Depth");

		Options.flatBedrockEnabled = ymlFileConfig.getBoolean("FlatBedrock.Enabled");
		int thickness = ymlFileConfig.getInt("FlatBedrock.Thickness");
		Options.flatBedrockThickness = Math.max(1, Math.min(3, thickness));

		try {
			Options.worldBiome = Biome.valueOf(ymlFileConfig.getString("World.Biome"));
		} catch (Exception e) {
			Options.worldBiome = Biome.PLAINS;
			ServerLog.err("§2Invalid Biome name: " + ymlFileConfig.get("World.Biome") + " . [" + e.getMessage() + "]");
		}

		Options.worldGrassChance = ymlFileConfig.getInt("World.GrassChance");
		Options.worldFlowerChance = ymlFileConfig.getInt("World.FlowerChance");
		Options.worldTreeChance = ymlFileConfig.getInt("World.TreeChance");

		for (String treeTypeString : ymlFileConfig.getStringList("World.TreeTypes")) {
			try {
				Options.worldTreeTypes.add(TreeType.valueOf(treeTypeString));
			} catch (Exception e) {
				ServerLog.err("§2Invalid TreeType name: " + treeTypeString + " . [" + e.getMessage() + "]");
			}
		}

		Options.worldGenerateWater = ymlFileConfig.getBoolean("World.GenerateWater");

		Options.worldOres.clear();
		Options.worldOreChances.clear();
		Options.worldOreNames.clear();
		for (String oreEntry : ymlFileConfig.getStringList("World.Ores")) {
			try {
				String[] parts = oreEntry.split(":");
				if (parts.length == 3) {
					Material material = processMaterial(parts[0], VersionManager.getBukkitVersion());
					int maxHeight = Integer.parseInt(parts[1]);
					int chance = Math.max(1, Math.min(100, Integer.parseInt(parts[2])));
					Options.worldOres.add(material);
					Options.worldOreChances.add(chance);
					Options.worldOreNames.add(parts[0]);
				}
			} catch (Exception e) {
				ServerLog.err("§2Invalid ore entry: " + oreEntry + " . [" + e.getMessage() + "]");
			}
		}

		Options.worldUndergroundMaterialNames.clear();
		Options.worldPreGroundMaterialNames.clear();
		Options.worldGroundMaterialNames.clear();
		Options.worldWaterGroundMaterialNames.clear();

		for (String strMaterial : ymlFileConfig.getStringList("World.UndergroundMaterials")) {
			Options.worldUndergroundMaterials.add(processMaterial(strMaterial, VersionManager.getBukkitVersion()));
			Options.worldUndergroundMaterialNames.add(strMaterial);
		}

		for (String strMaterial : ymlFileConfig.getStringList("World.PreGroundMaterials")) {
			Options.worldPreGroundMaterials.add(processMaterial(strMaterial, VersionManager.getBukkitVersion()));
			Options.worldPreGroundMaterialNames.add(strMaterial);
		}

		for (String strMaterial : ymlFileConfig.getStringList("World.GroundMaterials")) {
			Options.worldGroundMaterials.add(processMaterial(strMaterial, VersionManager.getBukkitVersion()));
			Options.worldGroundMaterialNames.add(strMaterial);
		}

		for (String strMaterial : ymlFileConfig.getStringList("World.WaterGroundMaterials")) {
			Options.worldWaterGroundMaterials.add(processMaterial(strMaterial, VersionManager.getBukkitVersion()));
			Options.worldWaterGroundMaterialNames.add(strMaterial);
		}

		File fileMessages = new File("plugins/AlmostFlatLandsReloaded/Messages.yml");
		FileConfiguration ymlFileMessage = YamlConfiguration.loadConfiguration(fileMessages);

		if (!fileMessages.exists()) {
			try {
				ymlFileMessage.set("Version", UpdateListener.getUpdateDoubleVersion());
				ymlFileMessage.set("[AlmostFlatLandsReloaded]", "&2[&a&lAFLR&2] ");
				ymlFileMessage.set("Color.1", "&a");
				ymlFileMessage.set("Color.2", "&2");
				ymlFileMessage.set("Message.1", "You have to be a player, to use this command.");
				ymlFileMessage.set("Message.2", "You do not have the permission for this command.");
				ymlFileMessage.set("Message.3", "There is a new update available for this plugin. &f( https://fof1092.de/Plugins/AFLR )&6");
				ymlFileMessage.set("Message.4", "The plugin is reloading...");
				ymlFileMessage.set("Message.5", "Reloading completed.");
				ymlFileMessage.set("Message.6", "Try [COMMAND]");
				ymlFileMessage.set("HelpTextGui.1", "&2[&aClick to use this command&2]");
				ymlFileMessage.set("HelpTextGui.2", "&2[&aNext page&2]");
				ymlFileMessage.set("HelpTextGui.3", "&2[&aLast page&2]");
				ymlFileMessage.set("HelpTextGui.4", "&2&oPage [PAGE]. &2Click on the arrows for the next page.");
				ymlFileMessage.set("HelpText.1", "This command shows you the help page.");
				ymlFileMessage.set("HelpText.2", "This command shows you the info page.");
				ymlFileMessage.set("HelpText.3", "This command is reloading the Config.yml and Messages.yml file.");
				ymlFileMessage.save(fileMessages);
			} catch (IOException e1) {
				ServerLog.err("Can't create the Messages.yml. [" + e1.getMessage() + "]");
			}
		}

		Options.msg.put("[AlmostFlatLandsReloaded]", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("[AlmostFlatLandsReloaded]")));
		Options.msg.put("color.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1")));
		Options.msg.put("color.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.2")));
		Options.msg.put("msg.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.1")));
		Options.msg.put("msg.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.2")));
		Options.msg.put("msg.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.3")));
		Options.msg.put("msg.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.4")));
		Options.msg.put("msg.5", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.5")));
		Options.msg.put("msg.6", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("Color.1") + ymlFileMessage.getString("Message.6")));
		Options.msg.put("helpTextGui.1", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.1")));
		Options.msg.put("helpTextGui.2", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.2")));
		Options.msg.put("helpTextGui.3", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.3")));
		Options.msg.put("helpTextGui.4", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpTextGui.4")));

		HelpPageListener.initializeHelpPageListener("/AlmostFlatLandsReloaded help", Options.msg.get("[AlmostFlatLandsReloaded]"));

		CommandListener.addCommand(new Command("/AFLR help (Page)", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.1"))));
		CommandListener.addCommand(new Command("/AFLR info", null, ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.2"))));
		CommandListener.addCommand(new Command("/AFLR reload", "AlmostFlatLandsReloaded.Reload", ChatColor.translateAlternateColorCodes('&', ymlFileMessage.getString("HelpText.3"))));
	}

	@Override
	public void onDisable() {
		System.out.println("[AlmostFlatLandsReloaded] a Plugin by F_o_F_1092");

		AlmostFlatlandsReloaded.disable();
	}

	/**
	 * Provides the logic for disabling the plugin.
	 */
	public static void disable() {
		CommandListener.clearCommands();

		Options.msg.clear();
		Options.worldTreeTypes.clear();
		Options.worldOres.clear();
		Options.worldOreChances.clear();
		Options.worldOreNames.clear();
		Options.worldUndergroundMaterials.clear();
		Options.worldPreGroundMaterials.clear();
		Options.worldGroundMaterials.clear();
		Options.worldWaterGroundMaterials.clear();
	}

	/**
	 * Processes material strings with optional data values (e.g., "STONE:5" or "ANDESITE").
	 * Maps modern materials to legacy equivalents for older versions.
	 */
	private static Material processMaterial(String strMaterial, BukkitVersion version) {
		String materialName = strMaterial;
		if (strMaterial.contains(":")) {
			String[] parts = strMaterial.split(":");
			materialName = parts[0];
		}
		if (version == BukkitVersion.v1_8_R3 ||
				version == BukkitVersion.v1_9_R1 ||
				version == BukkitVersion.v1_9_R2 ||
				version == BukkitVersion.v1_10_R1 ||
				version == BukkitVersion.v1_11_R1 ||
				version == BukkitVersion.v1_12_R1) {
			switch (materialName) {
				case "ANDESITE":
				case "GRANITE":
				case "DIORITE":
					materialName = "STONE";
					break;
				case "GRASS_BLOCK":
					materialName = "GRASS";
					break;
			}
		}
		try {
			return Material.valueOf(materialName);
		} catch (IllegalArgumentException e) {
			ServerLog.err("Invalid material name: " + strMaterial);
			return Material.STONE; // Fallback to STONE
		}
	}

	/**
	 * Provides the AFLR World generator depending on the Minecraft version.
	 */
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		ChunkGenerator chunkGenerator = null;

		try {
			Class<?> c;
			if (VersionManager.getBukkitVersion() == BukkitVersion.v1_8_R3 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_9_R2 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_10_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_11_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_12_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_13_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_13_R2 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_14_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_15_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R1 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R2 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_16_R3 ||
					VersionManager.getBukkitVersion() == BukkitVersion.v1_17_R1) {
				c = Class.forName("de.fof1092.almostflatlandsreloaded.worldgenerator." + VersionManager.getBukkitVersion() + ".WorldGenerator");
			} else {
				c = Class.forName("de.fof1092.almostflatlandsreloaded.worldgenerator.v1_18_R1_AND_ABOVE.WorldGenerator");
			}
			Constructor<?> m = c.getConstructor();
			Object i = m.newInstance();
			chunkGenerator = (ChunkGenerator) i;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chunkGenerator;
	}

	@Override
	public BiomeProvider getDefaultBiomeProvider(String worldName, String id) {
		BiomeProvider biomeProvider = null;

		try {
			Class<?> c = Class.forName("de.fof1092.almostflatlandsreloaded.worldgenerator.v1_18_R1_AND_ABOVE.WorldBiomeGenerator");
			Constructor<?> m = c.getConstructor();
			Object i = m.newInstance();
			biomeProvider = (BiomeProvider) i;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return biomeProvider;
	}
}
