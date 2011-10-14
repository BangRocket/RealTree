package us.bangrocket.realtree;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class RTOvergrow 
{
	public static RealTree plugin; 
	
	public RTOvergrow(RealTree instance)
	{
        plugin = instance;
	}
	
	private LinkedList<Block> validBlocks = new LinkedList<Block>();
	private LinkedList<Block> invalidBlocks = new LinkedList<Block>();
	
	private void loopThrough(Block block, Location loc1, Location loc2)
	{
	    int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
	        miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
	        minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
	        maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
	        maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
	        maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

	    for(int x = minx; x<=maxx;x++)
	    {
	        for(int y = miny; y<=maxy;y++)
	        {
	            for(int z = minz; z<=maxz;z++)
	            {
	            	
	            	Block tempBlock = block.getWorld().getBlockAt(x,y,z);
	            	
	            	if ((tempBlock.getType().equals(Material.GRASS) || 
	            		(tempBlock.getType().equals(Material.DIRT)) ||
	            		(tempBlock.getType().equals(Material.AIR))))
	            	{
	            		validBlocks.push(tempBlock);
	            	}
	            }
	        }
	    }
	    block.setType(Material.LOG);
	    
	    //if validBlocks.containsAll(c)
	    
	    while (!validBlocks.isEmpty())
	    {
	    	Block checkBlock =  validBlocks.pop();
	    
	    	if (!invalidBlocks.contains(checkBlock) || !invalidBlocks.contains(checkBlock.getRelative(BlockFace.UP)))
	    	{
	    		if (checkBlock.getType().equals(Material.GRASS) || checkBlock.getType().equals(Material.DIRT))
	    			{
	    				if (checkBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR))
	    				{
	    					if (!nearbyMaterial(checkBlock.getRelative(BlockFace.UP), Material.SAPLING))
	    						checkBlock.getRelative(BlockFace.UP).setType(Material.SAPLING);
	    				}
	    			
	    			}
	    	}
	    	
	    } 
	    
	    invalidBlocks.clear();
	    validBlocks.clear();
	    
	}

	private boolean isTouching(Block block) {

		for(int z = -1; z <= 1; z++)
		{
		  for(int x = -1; x <= 1; x++)
		  {
		    for(int y = -1; y <= 1; y++)
		    {
		    	invalidBlocks.add(block.getWorld().getBlockAt(x, y, z));
		    }
		  }
		}

		return true;
	}

	private boolean nearbyMaterial(Block block, Material mat)
	{
		
		if 	((block.equals(mat)) ||
			(block.getRelative(BlockFace.UP).getType().equals(mat)) ||
			(block.getRelative(BlockFace.DOWN).getType().equals(mat)) ||
			(block.getRelative(BlockFace.EAST).getType().equals(mat)) ||
			(block.getRelative(BlockFace.WEST).getType().equals(mat)) ||
			(block.getRelative(BlockFace.NORTH).getType().equals(mat)) ||
			(block.getRelative(BlockFace.SOUTH).getType().equals(mat)) ||
			(block.getRelative(BlockFace.NORTH_EAST).getType().equals(mat)) ||
			(block.getRelative(BlockFace.NORTH_WEST).getType().equals(mat)) ||
			(block.getRelative(BlockFace.SOUTH_EAST).getType().equals(mat)) ||
			(block.getRelative(BlockFace.SOUTH_WEST).getType().equals(mat)))
			return true;
				
		return false;
	}

	public void cuboidtest(Block block)
	{
		int h = 3; //number should be h-1 to match h value
					
		Block blockmin1 = block.getWorld().getBlockAt(block.getX()+h, block.getY()+h, block.getZ()+h); //note y-1!
		Location x1 = blockmin1.getLocation();
		Block blockmax1 = block.getWorld().getBlockAt(block.getX()-h, block.getY()-h, block.getZ()-h);
		Location y1 = blockmax1.getLocation();
		
		//invalidBlocks.add(block);
		isTouching(block);
		plugin.output(Integer.toString(invalidBlocks.size()));
		
		this.loopThrough(block, x1, y1);

	}
	
}
