package us.bangrocket.realtree;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class RTOvergrow 
{
	public static RealTree plugin; 
	
	public RTOvergrow(RealTree instance)
	{
        plugin = instance;
	}
	
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
	            	Block test = block.getWorld().getBlockAt(x, y, z);
	                if (test.getType().equals(Material.GRASS) || test.getType().equals(Material.DIRT))
	                	test.setType(Material.AIR);
	                else
	                	test.setType(Material.GRASS);
	            }
	        }
	    }
	    block.setType(Material.LOG);
	}

	public void cuboidtest(Block block)
	{
		int h = 4; //number should be h-1 to match h value
		
		//Important note! X,Y,Z is a 3d directional, so, x and z operate on a flat plane not x and y. DONT FORGET IT AGAIN.
		
		Block blockmin1 = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ()); //note y-1!
		Location x1 = blockmin1.getLocation();
		Block blockmax1 = block.getWorld().getBlockAt(block.getX()+(h), block.getY()-1, block.getZ()+(h));
		Location y1 = blockmax1.getLocation();
		
		Block blockmin2 = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ()); //note y-1!
		Location x2 = blockmin2.getLocation();
		Block blockmax2 = block.getWorld().getBlockAt(block.getX()-(h), block.getY()-1, block.getZ()-(h));
		Location y2 = blockmax2.getLocation();
		
		this.loopThrough(block, x1, y1);
		this.loopThrough(block, x2, y2);
		
	}
	
}
