package us.bangrocket.realtree;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;


public class RTTaskMan
{
	public static RealTree plugin; 
	
	public RTTaskMan(RealTree instance)
	{
        plugin = instance;
	}
	
	private int taskID = 0;
		
	public int startCycleReplantTask() //OK! This actually will start a time following the day and night cycle, replacing the trees during the night, all at once.
	{
		
		//connect this to world time of day, define world(s) by config. for now, just world.
		//ok! you know how many ticks are in a day (14k), and you'll want to wait a little after that so you're in full night
		//so add maybe another +1000 ticks. You also need to make sure you're synced to the world clock, so you're gonna need
		//to find the difference between the current time and the time of night first. The result of that will be the initial
		//delay. then, you'll need to use the length of a day to determine how often to cycle.
		
		//long delayTime = plugin.getdelayTime();
		
		taskID = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, //change to AsyncDelayedRepeatingTask()
		
		new Runnable()
		{
			public void run()
			{							
				
				//for (World world : plugin.getServer().getWorlds())
				//{
			    //   long time = world.getTime();
			    //    plugin.output(world.getName().toString() + " " + Long.toString(time));
				//}
				
					if (!(plugin.getReplant().isEmpty()))
					{
						while (!(plugin.getReplant().isEmpty()))
						{
								
							//consider adding a delay in this function by adding a wait(random)
							BlockState originalState = plugin.getReplant().pop();
							
							//This is the only problem with tracking BlockStates. I can't determine what the block
							//currently is without these two lines. However, I think this is better than tracking 
							//the data in another array/list.
							BlockState currentState = originalState;
							currentState.update();
							
							Block current = currentState.getBlock();
							
							if( (current.getType().equals(Material.AIR)) || (current.getType().equals(Material.FIRE)) ) //just incase it was on fire
							{ 
								//to keep the saping fitted to the same tree type we just need to keep the datavalue
								current.setType(Material.SAPLING);
								current.setData(originalState.getRawData());
								
								//protect.add(current); 
							}
						}
					}
			}
		},60L, plugin.getConfig().getdelayTime()*20);
		
		plugin.output("Replant task started!");
		return taskID;
	}
	
	public void stopCycleReplantTask()
	{		
		plugin.getServer().getScheduler().cancelTask(this.taskID);
		
		plugin.output("Replant task stopped!");
	}

	public void startReplantTask(final BlockState originalState)
	{
	
		final BlockState currentState = originalState;
		currentState.update();
		
		final Block block = currentState.getBlock();
		
		plugin.output("Block replant start at " + plugin.getXYZ(block));
	
		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin,
			new Runnable()
			{
				public void run()
				{	    										
					plugin.output("Block replant end " + plugin.getXYZ(block));
				
					plugin.getOvergrow().startOvergrow(originalState);
					
					if((block.getType().equals(Material.AIR)) || (block.getType().equals(Material.FIRE))) //just incase it was on fire
					{ 
						if ((block.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT)) ||
								block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS)) //makes sure the block below is still valid
						{
							//to keep the saping fitted to the same tree type we just need to keep the datavalue
							block.setType(Material.SAPLING);
							block.setData(originalState.getRawData());
    					
							if ((plugin.getConfig().isProtectEnabled()) && (!plugin.getConfig().isProtectBeforeSap()))
							{
								plugin.getTaskMan().startProtectTask(block);
							}
						}
					}
				}
			},plugin.getConfig().getdelayTime()*20);
		
	}
	public void startProtectTask(final Block block)
	{
				
		plugin.output("Block protection start at " + plugin.getXYZ(block));
		plugin.getProtect().add(block);
	
		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin,
			new Runnable()
			{
				public void run()
				{	    										
					plugin.output("Block protection end " + plugin.getXYZ(block));
					plugin.getProtect().remove(block);
				}
			},plugin.getConfig().getProtectTime()*20);
	}
}
