package me.JJDev256.CrazyScenarios.CaveWorld;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;


public class CustomChunkGenerator extends ChunkGenerator {
	
	private SimplexOctaveGenerator noise = null;
	private ArrayList<Material> oresReplaceMaterials = null;
	
	public CustomChunkGenerator() {
		oresReplaceMaterials = new ArrayList<Material>();
		oresReplaceMaterials.add(Material.STONE);
		oresReplaceMaterials.add(Material.DIRT);
		oresReplaceMaterials.add(Material.GRAVEL);
		oresReplaceMaterials.add(Material.DIORITE);
		oresReplaceMaterials.add(Material.GRANITE);
		oresReplaceMaterials.add(Material.ANDESITE);
 	}
	
	private void generateOreVein(ChunkData chunk, Random random, int X, int Y, int Z, int size, Material block) {
		int x = X;
		int y = Y;
		int z = Z;
		for (int j = 0; j < size; j++) {
			switch (random.nextInt(3)) {
			case 0:
				x += random.nextInt(2)*2-1;
				break;
			case 1:
				y += random.nextInt(2)*2-1;
				break;
			case 2:
				z += random.nextInt(2)*2-1;
				break;
			}
			if (y >= 0 && y < 256 && x >= 0 && x < 16 && z >= 0 && z < 16 && oresReplaceMaterials.contains(chunk.getType(x, y, z)))
				chunk.setBlock(x, y, z, block);
		}
	}
	
	private void generateOres(ChunkData chunk, Random random, int tries, int size, Material block) {
		for (int i = 0; i < tries; i++)
			generateOreVein(chunk, random, random.nextInt(16), random.nextInt(256), random.nextInt(16), size, block);
	}
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		if (noise == null)
			noise = new SimplexOctaveGenerator(new Random(world.getSeed()), 4);
		ChunkData chunkData = createChunkData(world);
		noise.setScale(0.08);
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				for (int y = 0; y < 256; y++)
					if (noise.noise(chunkX*16+x, y, chunkZ*16+z, 1, 1) - Math.pow(1-y/128f,6f) < 0.3)
						chunkData.setBlock(x, y, z, Material.STONE);
					else if (y < 16)
						chunkData.setBlock(x, y, z, Material.LAVA);
		generateOres(chunkData, random, 80, 24, Material.DIRT);
		generateOres(chunkData, random, 80, 24, Material.GRAVEL);
		generateOres(chunkData, random, 80, 24, Material.GRANITE);
		generateOres(chunkData, random, 80, 22, Material.DIORITE);
		generateOres(chunkData, random, 80, 24, Material.GRANITE);
		generateOres(chunkData, random, 60, 16, Material.COAL_ORE);
		generateOres(chunkData, random, 40, 8, Material.IRON_ORE);
		generateOres(chunkData, random, 20, 8, Material.GOLD_ORE);
		generateOres(chunkData, random, 10, 8, Material.REDSTONE_ORE);
		generateOres(chunkData, random, 8, 6, Material.LAPIS_ORE);
		generateOres(chunkData, random, 6, 8, Material.DIAMOND_ORE);
		generateOres(chunkData, random, 4, 2, Material.EMERALD_ORE);
		
		generateOres(chunkData, random, 60, 1, Material.WATER);
		return chunkData;
	}
}
