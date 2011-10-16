package us.bangrocket.realtree;

import java.util.LinkedList;
import java.util.Random;

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
	private Random blockSelect = new Random();
	
	//placeholder varibles for things that will eventually be in ConfigMan
	int minR = 1; 
	int maxR = 6;
	int numReplant = 3;

	private void loopThrough(Block block, Location loc1, Location loc2)
	{
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
	    			if (selBlock.getType().equals(Material.AIR))
	    			{
	    					//check the block below
	    					if (((selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT)) || 
	    						(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS)) ||
	    						(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.SNOW)) ||
	    						(selBlock.getRelative(BlockFace.DOWN).getType().equals(Material.LONG_GRASS))) && 
	    						(selBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)))
	    					{
	    						selBlock.setType(Material.SAPLING);
	    						selBlock.setData(block.getData());
	    						x++; 
	    					}

	    					if ((selBlock.getType().equals(Material.DIRT)) || 
	    						(selBlock.getType().equals(Material.GRASS)) ||
	    						(selBlock.getType().equals(Material.SNOW)) ||
	    						(selBlock.getType().equals(Material.LONG_GRASS)))
	    					{
	    						//check the block below
	    						if ((selBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)))
	    						{
	    							if ((selBlock.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR)))
	    							{
	    								selBlock.setType(Material.SAPLING);
	    								selBlock.setData(block.getData());
	    								x++;
	    							}
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}

	    validBlocks.clear();
	    
	}
	  
	public void cuboidtest(Block block)
	{
		
		Block blockmin1 = block.getWorld().getBlockAt(block.getX()+maxR, block.getY()+maxR, block.getZ()+maxR);
		Location x1 = blockmin1.getLocation();
		Block blockmax1 = block.getWorld().getBlockAt(block.getX()-maxR, block.getY()-maxR, block.getZ()-maxR);
		Location y1 = blockmax1.getLocation();

		this.loopThrough(block, x1, y1);

	}

}