package us.bangrocket.realtree;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockListener;


public class RTBlockListener extends BlockListener{
	
	public static RealTree plugin; 
	
	public RTBlockListener(RealTree instance)
	{
        plugin = instance;
	}

	public void onBlockBreak(BlockBreakEvent event)
	{
    	if (plugin.getConfig().isRTEnabled())  		
    	{    		
    		Block block = event.getBlock();
    		Player player = event.getPlayer();
    		
    		//Okay so lets check if the player has broken a log block!
    		if(block.getType() == Material.LOG)
    		{
    			//kick out of the loop unless we've got access to work in creative mode
        		if ((player.getGameMode().equals(GameMode.CREATIVE)) && !(plugin.getConfig().getcreativeMode()))
        		{
        			//DEBUGGERY
        			//return;
        			//DEBUGGERY
        		}
        		
				//block is broken! Now lets see if this block was on a piece of dirt...
    			Block blockUnder = block.getRelative(BlockFace.DOWN);
    			
    			if((blockUnder.getType() ==  Material.DIRT)  ||  (blockUnder.getType() ==  Material.GRASS))
    			{
    				//on grass here so lets plant the sapling! wooooo!
    				if (true)//(player.hasPermission("livingforest.replant.chopped")) //TODO: User file permissions/RT perms
    				{
    					
    					if (plugin.getConfig().getcycleReplant())
    					{
    						plugin.getReplant().add(event.getBlock().getState());
    					}
    					else
    					{
    						plugin.getTaskMan().startReplantTask(block.getState());
    					}
	    				
    					if (plugin.getConfig().isProtectEnabled() && (plugin.getConfig().isProtectBeforeSap()))
    					{
    						plugin.getTaskMan().startProtectTask(block);
    					}
    					
    					if(plugin.getConfig().isTellUserPlanted())
    					{
    						plugin.output(plugin.getConfig().getTellUserPlanted(), player, ChatColor.GREEN);
    					}
    				}
    			}
    		}
    		else if(block.getType() == Material.SAPLING) //Not a log, maybe they are trying to break a protected sapling?
    		{
    			if(plugin.getProtect().contains(block))
    			{
    				event.setCancelled(true);
    				
    				if(plugin.getConfig().isTelluserProtected())
    				{
    					plugin.output(plugin.getConfig().getTellUserProtected(), player, ChatColor.GREEN);
    				}
    			}
    		}
    		
    		if ((block.getType() == Material.DIRT) || (block.getType() == Material.GRASS)) //How about the dirt under the sapling?
    		{
    			//The sapling is one above the dirt, so get the block there instead
    			Block sap = block.getRelative(BlockFace.UP);
    			if(plugin.getProtect().contains(sap))
    			{
    				event.setCancelled(true);
    				
    				if(plugin.getConfig().isTelluserProtected())
    				{
    					//TODO: put this string into main class.
    					plugin.output("This block is protected for a while.", player, ChatColor.GREEN);
    				}
    			}
    		}
    	}
	}

	
	public void onBlockBurn(BlockBurnEvent event)
	{
		
		if (!plugin.getConfig().isRTEnabled())
		{
			return;
		}
		
		//thanks hawox
		Block block = event.getBlock();
		
		if(block.getType() == Material.LOG)
		{ 
			if(plugin.isEnabled() == true)
			{
				//block is broken! Now lets see if this block was on a piece of dirt...
				Block block_under_me = block.getRelative(BlockFace.DOWN);
				if ((block_under_me.getType() ==  Material.DIRT)  ||  (block_under_me.getType() ==  Material.GRASS) )
				{
					
					//since i'm using blockstates, its a bit important that I do this first. 
					//TODO: Stop being lazy and fix this.
					plugin.getTaskMan().startReplantTask(block.getState());
					
					//It will not replant the block if it is not air, so lets help that burning log out
					//BR: this only works because of the delay on my ReplantTask.
					block.setType(Material.AIR);
					
				}
			}
		}
	}
}