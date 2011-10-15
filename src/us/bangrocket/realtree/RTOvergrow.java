package us.bangrocket.realtree;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class RTOvergrow 
{
	public static RealTree plugin; 
	
	public RTOvergrow(RealTree instance)
	{
        plugin = instance;
	}

	public void cuboidtest(Block block) //block is the bottom log/sapling block
	{
		int maxR = 8; //temp radius value (values from LF config)
		int minR = 2; 
		
		int originalX = block.getX(); //master varis
		int originalY = block.getY();
		int originalZ = block.getZ();
		
		int tempX, tempY, tempZ ; //looping varis
 		
 		int diffX, diffY, diffZ; //difference varis
 		
 		int slopeDistance = 0;
		
		for(int x = 0; x<=(originalX+(maxR^2)); x++)
	    {
			tempX = (originalX - x);
			tempY = originalY;
			for(int y = 0; y<=(originalY+(maxR^2)); y++)
	        {
				tempZ = originalZ;
				tempY = (originalY - y);
	            for(int z = 0; z<=(originalZ+(maxR^2)); z++)
	            {
	            	tempZ = (originalZ - 2);
	            	diffX = tempX - originalX;
	            	diffY = tempY - originalY;
	            	diffZ = tempZ - originalZ;
	            	
	            	slopeDistance = (diffX^2) + (diffY^2) + (diffZ^2); 
	            	
	            	//if ((slopeDistance <= (maxR^2)) && (slopeDistance >= (minR^2)))
	            	{
	            		Block tempBlock = block.getWorld().getBlockAt(tempX, tempY, tempZ);
	            		
	            		tempBlock.setType(Material.BEDROCK);
	            		
	            	}
	            }
	        }
	    }

	}
	
}
