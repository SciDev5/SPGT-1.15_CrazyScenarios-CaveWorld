package me.JJDev256.CrazyScenarios.CaveWorld;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		
	}
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new CustomChunkGenerator();
	}
}
