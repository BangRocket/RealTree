package us.bangrocket.realtree;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

//This code is mosltly derived from Kostronor's Fastgrow. I took some liberties with it, but it was much easier to just use
//his implementation than to write it from scratch. The code that I changes is marked with //BR.

public class RTFastgrow extends BlockListener
{
	
	public static RealTree plugin; 
	
	public RTFastgrow(RealTree instance)
	{
        plugin = instance;
	}

	public void onBlockPlace(BlockPlaceEvent event)
	{
//		if ((!plugin.getConfig().isFGEnabled()) || (!plugin.getConfig().isRTEnabled()))
//			return;
//		
//		if (!(event.getPlayer().hasPermission("livingforest.fastgrow.use") || !(event.getPlayer().hasPermission("realtree.fastgrow.enabled"))))
//			return;
//		
//		if (plugin.getPermMan().userFileLoaded)
//		{
//			if (!(plugin.getPermMan().isUserAllowed(event.getPlayer().getName())))
//				return;
//		}
		
		//BR- replaced all TypeID references with getType(). Reads better IMO.
		if (event.getBlock().getType() == Material.SAPLING)
		{
			if ((plugin.getConfig().isFGEnabled()) && (plugin.getConfig().isRTEnabled()) && ((plugin.getPermMan().isUserAllowed(event.getPlayer().getName()))))
			{

				final int randtime = (int)(Math.random()*plugin.getConfig().getFGRandtime()+plugin.getConfig().getFGBasetime());

				final Block block = event.getBlock();
				final TreeType treetype = getTree(block.getData());
				final World world = event.getPlayer().getWorld();

				//removed the second itemstack and set this one up to also support the different kind of saplings.
				final ItemStack istack = new ItemStack(Material.SAPLING, 1 , (short) 0 , block.getData());

				//changed this to an Async task because I think multiple should be able to run at once and
				//also because it matchs most of my other scheduled tasks.
				event.getPlayer().getServer().getScheduler().scheduleAsyncDelayedTask(plugin,
						new Runnable()
				{
					public void run()
					{	
						//plugin.output("fastgrow activity");
						if (block.getType().equals(Material.SAPLING))
						{
							block.setType(Material.AIR);
							if (!world.generateTree(block.getLocation(), treetype))
							{
								world.dropItemNaturally(block.getLocation(), istack);
							}
						}

					}
				},randtime*20);
			}
		}
	}

	public TreeType getTree(int data){
		switch (data){
		case 0:
			if (!(((int)(Math.random() * 100)) <= plugin.getConfig().getFGBigTree()))
			{
				return TreeType.TREE;	
			}
			else
			{
				return TreeType.BIG_TREE;
			}
		case 1:
			if (!(((int)(Math.random() * 100)) <= plugin.getConfig().getFGRedwood()))
			{
				return TreeType.REDWOOD;	
			}
			else
			{
				return TreeType.TALL_REDWOOD;
			}
		case 2:
			return TreeType.BIRCH;	


		}
		return TreeType.TREE;
	}

	public void startFastGrow(final Block block)
	{
		final int randtime = (int)(Math.random()*plugin.getConfig().getFGRandtime()+plugin.getConfig().getFGBasetime());

		final World world = block.getWorld();
		final TreeType treetype = plugin.getFastgrow().getTree(block.getData());
		final ItemStack istack = new ItemStack(Material.SAPLING, 1 , (short) 0 , block.getData());

		plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin,
				new Runnable()
		{
			public void run()
			{	
				if (!world.generateTree(block.getLocation(), treetype))
				{
					block.setType(Material.AIR);
					world.dropItemNaturally(block.getLocation(), istack);
				}
			}

		},randtime*20);
	}
	
}
