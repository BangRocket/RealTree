package us.bangrocket.realtree;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

public class RTOvergrow  //TODO: make this a standalone body of code like Fastgrow
{
	public static RealTree plugin; 

	public RTOvergrow(RealTree instance)
	{
		plugin = instance;
	}

	//I'd really like this to be a private function, but at this point I don't think it's that big of a deal.
	public void startOvergrow(BlockState block)
	{
		LinkedList<Block> validBlocks = new LinkedList<Block>();	
		Random blockSelect = new Random();

		//placeholder varibles for things that will eventually be in ConfigMan
		int minR = plugin.getConfig().getOGminRadius(); 
		int maxR = plugin.getConfig().getOGmaxRadius(); 
		int numReplant = plugin.getConfig().getOGExtraTrees();

		Block blockmin1 = block.getWorld().getBlockAt(block.getX()+maxR, block.getY()+maxR, block.getZ()+maxR);
		Location loc1 = blockmin1.getLocation();

		Block blockmax1 = block.getWorld().getBlockAt(block.getX()-maxR, block.getY()-maxR, block.getZ()-maxR);
		Location loc2 = blockmax1.getLocation();

		int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
			miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
			minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
			maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
			maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
			maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		int masterX = block.getX(),
			masterY = block.getY(),
			masterZ = block.getZ();

		for(int x = minx; x<=maxx;x++)
		{
			for(int y = miny; y<=maxy;y++)
			{
				for(int z = minz; z<=maxz;z++)
				{

					Block tempBlock = block.getWorld().getBlockAt(x,y,z);

					if ((tempBlock.getType().equals(Material.GRASS)) || 
							(tempBlock.getType().equals(Material.DIRT)) ||
							(tempBlock.getType().equals(Material.SNOW)) ||
							(tempBlock.getType().equals(Material.LONG_GRASS)) ||
							(tempBlock.getType().equals(Material.AIR)))

					{
						validBlocks.push(tempBlock);

					}
				}
			}
		}

		//for debug purposes
		// block.setType(Material.LOG);

		for (int x = 0; x < numReplant;)
		{

			int selected = blockSelect.nextInt(validBlocks.size());
			Block selBlock = validBlocks.get(selected);

			if (!((selBlock.getX() == masterX) && (selBlock.getY() == masterY) && (selBlock.getZ() == masterZ)))
			{
				if (!((selBlock.getX() == masterX+minR) && (selBlock.getY() == masterY+minR) && (selBlock.getZ() == masterZ+minR)))
				{
					if (selBlock.getType().equals(Material.SAPLING))
					{
						continue;
					}

					//Air. (block below, air above?)
					if (selBlock.getType().equals(Material.AIR))
					{
						//lets make sure we're not too close to other trees
						if (!((selBlock.getRelative(BlockFace.NORTH).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.EAST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.NORTH_EAST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.NORTH_WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.LOG))) &&
								!((selBlock.getRelative(BlockFace.NORTH).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.EAST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.NORTH_EAST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.NORTH_WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.SAPLING))))
						{
							//check for growable spots (this includes air above)
							if (((selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT)) || 
									(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS)) ||
									(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.SNOW)) ||
									(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.LONG_GRASS))) && 
									(selBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR))) 
							{

								selBlock.setType(Material.SAPLING);
								selBlock.setData(block.getRawData());
								x++; 

//								if (plugin.getConfig().isFGEnabled())
//								{
//									//TODO: Fix, implement this.
//									plugin.getFastgrow().startFastGrow(selBlock);
//								
//								}
							}
						}
					}

					//Dirt, Grass and Snow (air above?)
					if ((selBlock.getType().equals(Material.DIRT)) || 
							(selBlock.getType().equals(Material.GRASS)) ||
							(selBlock.getType().equals(Material.SNOW)) ||
							(selBlock.getType().equals(Material.LONG_GRASS)))
					{
						//lets make sure we're not too close to other trees
						if (!((selBlock.getRelative(BlockFace.NORTH).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.EAST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.NORTH_EAST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.NORTH_WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.LOG)) ||
								(selBlock.getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.LOG))) &&
								!((selBlock.getRelative(BlockFace.NORTH).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.EAST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.NORTH_EAST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.NORTH_WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.SAPLING)) ||
										(selBlock.getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.SAPLING))))
						{
							//check for growable spots (this includes air above)
							if ((selBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)))
							{
								if ((selBlock.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR)))
								{

									selBlock.getRelative(BlockFace.UP).setType(Material.SAPLING);
									selBlock.getRelative(BlockFace.UP).setData(block.getRawData());
									x++;
//									
//									if (plugin.getConfig().isFGEnabled())
//									{
//										//TODO: Fix, implement this.
//										plugin.getFastgrow().startFastGrow(selBlock);
//									
//									}
								}
							}
						}
					}
				}
			}
		}
		validBlocks.clear();
	}
}