package shedar.mods.ic2.nuclearcontrol.utils;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;

public class NuclearHelper {

	private static final double STEAM_PER_EU = 3.2D;
	private static final String ISTEAMREACTOR = "ic2.api.reactor.ISteamReactor";

	public static IReactor getReactorAt(World world, int x, int y, int z) {
		if (world == null)
			return null;
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof IReactor)
			return (IReactor) entity;
		return null;
	}
	
	
	public static boolean isSteam(IReactor reactor) {
		try{
			if(IC2NuclearControl.instance.crossClassic.isClassicSpeiger && reactor.getClass().isInstance(Class.forName(ISTEAMREACTOR)))
				return true;
		}catch(ClassNotFoundException e){} // No biggie
		
		return false;
	}

	public static int euToSteam(int eu) {
		return (int) Math.floor((eu) * STEAM_PER_EU);
	}

	public static IReactorChamber getReactorChamberAt(World world, int x, int y, int z) {
		if (world == null)
			return null;
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof IReactorChamber) {
			return (IReactorChamber) entity;
		}
		return null;
	}

	public static IReactor getReactorAroundCoord(World world, int x, int y, int z) {
		if (world == null)
			return null;
		ChunkPosition[] around = { new ChunkPosition(-1, 0, 0),
				new ChunkPosition(1, 0, 0), new ChunkPosition(0, -1, 0),
				new ChunkPosition(0, 1, 0), new ChunkPosition(0, 0, -1),
				new ChunkPosition(0, 0, 1) };
		IReactor ent = null;
		for (int i = 0; i < 6 && ent == null; i++) {
			ChunkPosition delta = around[i];
			ent = getReactorAt(world, x + delta.chunkPosX, y + delta.chunkPosY, z + delta.chunkPosZ);
		}
		return ent;
	}

	public static IReactorChamber getReactorChamberAroundCoord(World world, int x, int y, int z) {
		if (world == null)
			return null;
		ChunkPosition[] around = { new ChunkPosition(-1, 0, 0),
				new ChunkPosition(1, 0, 0), new ChunkPosition(0, -1, 0),
				new ChunkPosition(0, 1, 0), new ChunkPosition(0, 0, -1),
				new ChunkPosition(0, 0, 1) };
		IReactorChamber ent = null;
		for (int i = 0; i < 6 && ent == null; i++) {
			ChunkPosition delta = around[i];
			ent = getReactorChamberAt(world, x + delta.chunkPosX, y
					+ delta.chunkPosY, z + delta.chunkPosZ);
		}
		return ent;
	}

	public static boolean isProducing(IReactor reactor) {
		ChunkCoordinates position = reactor.getPosition();
		return reactor.getWorld().isBlockIndirectlyGettingPowered(position.posX, position.posY, position.posZ);
	}

	public static int getNuclearCellTimeLeft(ItemStack rStack) {
		int val = IC2NuclearControl.instance.crossIC2.getNuclearCellTimeLeft(rStack);
		return val;
	}

}
