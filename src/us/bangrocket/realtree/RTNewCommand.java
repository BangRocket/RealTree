package us.bangrocket.realtree;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RTNewCommand
{
	public static RealTree plugin; 

	public RTNewCommand(RealTree instance)
	{
		plugin = instance;
	}

	public boolean processCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		String prefix = "[" + plugin.pdfFile.getName() + "] ";

		plugin.output(Integer.toString(args.length));
		
		if ((cmd.getName().equalsIgnoreCase("rt")) || (cmd.getName().equalsIgnoreCase("realtree")))
		{
			//
			//SINGLE ITEM COMMMANDS (ie /realtree enable and /realtree reload)
			//
			if (args.length == 1)
			{
				if(args[0].equalsIgnoreCase("test")) 
				{
					//if ((sender.hasPermission("realtree.test")))
					//{
					plugin.output(ChatColor.AQUA + "Whammy-whoozle!", sender);
					//}
					return true;
				}
				
				//ENABLE Command
				if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("e")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.plugin"))) 
					{
						sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled globally!");
						if (!(plugin.getConfig().isRTEnabled()))
							plugin.getConfig().setRTEnabled(true);
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				//DISABLE Command
				if ((sender.isOp()) || (args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
				{

					if ((sender.hasPermission("realtree.toggle.plugin"))) 
					{
						sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled globally!");
						if ((plugin.getConfig().isRTEnabled()))
							plugin.getConfig().setRTEnabled(false);
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				//RELOAD command
				if ((sender.isOp()) || (args[0].equalsIgnoreCase("reload"))  || (args[0].equalsIgnoreCase("r")))  
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.config.reload"))) 
					{
						if (plugin.getConfig().isLFConfig())
						{
							sender.sendMessage(prefix + ChatColor.GREEN + "LivingForest config reloaded.");
						}
						else
						{
							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree config reloaded.");
						}
						plugin.getConfig().readConfig();

						sender.sendMessage(prefix + ChatColor.GREEN + "User file reloaded.");
						plugin.getPermMan().checkUserConfig();
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				
				//REPLANT command
				if ((args[0].equalsIgnoreCase("replant"))  || (args[0].equalsIgnoreCase("rp")))  
				{
					if ((sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "replant"))
						{
							plugin.getPermMan().disablePerm(sender.getName(),"replant");
							sender.sendMessage(prefix + ChatColor.RED + "Replant disabled for you.");
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(),"replant");
							sender.sendMessage(prefix + ChatColor.GREEN + "Replant enabled for you.");	
						}
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				
				//OVERGROW command
				if ((args[0].equalsIgnoreCase("overgrow"))  || (args[0].equalsIgnoreCase("og")))  
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "overgrow"))
						{
							plugin.getPermMan().disablePerm(sender.getName(),"overgrow");
							sender.sendMessage(prefix + ChatColor.RED + "Overgrow disabled for you.");
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(),"overgrow");
							sender.sendMessage(prefix + ChatColor.GREEN + "Overgrow enabled for you.");	
						}
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}

				//FASTGROW command
				if ((args[0].equalsIgnoreCase("fastgrow"))  || (args[0].equalsIgnoreCase("fg")))  
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "fastgrow"))
						{
							plugin.getPermMan().disablePerm(sender.getName(),"fastgrow");
							sender.sendMessage(prefix + ChatColor.RED + "Fastgrow disabled for you.");
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(),"fastgrow");
							sender.sendMessage(prefix + ChatColor.GREEN + "Fastgrow enabled for you.");	
						}
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
				return true;
			}
		}

		if (args.length == 2)
			//
			//DOUBLE ITEM COMMMANDS (ie /realtree enable me and /rt enable <player>)
			//
		{
			
			//ENABLE Command
			if ((args[0].equalsIgnoreCase("enable"))  || (args[0].equalsIgnoreCase("e")))  
			{
				if ((args[1].equalsIgnoreCase("me")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (!plugin.getPermMan().isUserAllowed(sender.getName(), "RealTree"))
						{								
							plugin.getPermMan().enablePerm(sender.getName(), "RealTree");
							sender.sendMessage(prefix + ChatColor.GREEN + "Realtree enabled for you.");
							return true;
						}
						else
						{
							sender.sendMessage(prefix + ChatColor.RED + "Realtree is already enabled for you!");
							return true;
						}
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				else
				{
					if ((plugin.getPermMan().isPlayerUser(args[1])))
					{	
						if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.other"))) 
						{
							if (!plugin.getPermMan().isUserAllowed(args[1], "RealTree"))
							{								
								plugin.getPermMan().enablePerm(args[1], "RealTree");
								sender.sendMessage(prefix + "Realtree enabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GREEN + "Realtree enabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								sender.sendMessage(prefix + ChatColor.RED + "Realtree is already enabled for player: " + args[1]);
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
				return true;
			}

			//DISABLE Command
			if ((args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
			{
				if ((args[1].equalsIgnoreCase("me")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "RealTree"))
						{								
							plugin.getPermMan().disablePerm(sender.getName(), "RealTree");
							sender.sendMessage(prefix + ChatColor.GREEN + "Realtree disabled for you.");
							return true;
						}
						else
						{
							sender.sendMessage(prefix + ChatColor.RED + "Realtree is already disabled for you!");
							return true;
						}
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				else
				{
					if ((plugin.getPermMan().isPlayerUser(args[1])))
					{	
						if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.other"))) 
						{
							if (plugin.getPermMan().isUserAllowed(args[1], "RealTree"))
							{								
								plugin.getPermMan().disablePerm(args[1], "RealTree");
								sender.sendMessage(prefix + "Realtree disabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GREEN + "Realtree disabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								sender.sendMessage(prefix + ChatColor.RED + "Realtree is already disabled for player: " + args[1]);
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
				return true;
			}
			
			//REPLANT Command
			if ((args[0].equalsIgnoreCase("replant"))  || (args[0].equalsIgnoreCase("rp")))  
			{
				if ((args[1].equalsIgnoreCase("me")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "replant"))
						{								
							plugin.getPermMan().disablePerm(sender.getName(), "replant");
							sender.sendMessage(prefix + ChatColor.RED + "Replant disabled for you.");
							return true;
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(), "replant");
							sender.sendMessage(prefix + ChatColor.GREEN + "Replant enabled for you.");
							return true;
						}
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				else
				{
					if ((plugin.getPermMan().isPlayerUser(args[1])))
					{	
						if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.other"))) 
						{
							if (plugin.getPermMan().isUserAllowed(sender.getName(), "replant"))
							{								
								plugin.getPermMan().disablePerm(args[1], "replant");
								sender.sendMessage(prefix + ChatColor.RED + "Replant disabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Replant disabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								plugin.getPermMan().enablePerm(args[1], "replant");
								sender.sendMessage(prefix + ChatColor.GREEN + "Replant enabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Replant enabled for you by: " + sender.getName());
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
				return true;
			}
			
			//OVERGROW Command
			if ((args[0].equalsIgnoreCase("overgrow"))  || (args[0].equalsIgnoreCase("og")))  
			{
				if ((args[1].equalsIgnoreCase("me")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "overgrow"))
						{								
							plugin.getPermMan().disablePerm(sender.getName(), "overgrow");
							sender.sendMessage(prefix + ChatColor.RED + "Overgrow disabled for you.");
							return true;
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(), "overgrow");
							sender.sendMessage(prefix + ChatColor.GREEN + "Overgrow enabled for you!");
							return true;
						}
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				else
				{
					if ((plugin.getPermMan().isPlayerUser(args[1])))
					{	
						if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.other"))) 
						{
							if (plugin.getPermMan().isUserAllowed(sender.getName(), "overgrow"))
							{								
								plugin.getPermMan().disablePerm(args[1], "overgrow");
								sender.sendMessage(prefix + ChatColor.RED + "Overgrow disabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Overgrow disabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								plugin.getPermMan().enablePerm(args[1], "overgrow");
								sender.sendMessage(prefix + ChatColor.GREEN + "Overgrow enabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Overgrow enabled for you by: " + sender.getName());
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
				return true;
			}
			
			//FASTGROW Command
			if ((args[0].equalsIgnoreCase("fastgrow"))  || (args[0].equalsIgnoreCase("fg")))  
			{
				if ((args[1].equalsIgnoreCase("me")))
				{
					if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.self"))) 
					{
						if (plugin.getPermMan().isUserAllowed(sender.getName(), "fastgrow"))
						{								
							plugin.getPermMan().disablePerm(sender.getName(), "fastgrow");
							sender.sendMessage(prefix + ChatColor.RED + "Fastgrow disabled for you.");
							return true;
						}
						else
						{
							plugin.getPermMan().enablePerm(sender.getName(), "fastgrow");
							sender.sendMessage(prefix + ChatColor.GREEN + "Fastgrow enabled for you!");
							return true;
						}
					}
					sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				else
				{
					if ((plugin.getPermMan().isPlayerUser(args[1])))
					{	
						if ((sender.isOp()) || (sender.hasPermission("realtree.toggle.other"))) 
						{
							if (plugin.getPermMan().isUserAllowed(sender.getName(), "fastgrow"))
							{								
								plugin.getPermMan().disablePerm(args[1], "fastgrow");
								sender.sendMessage(prefix + ChatColor.RED + "Fastgrow disabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Fastgrow disabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								plugin.getPermMan().enablePerm(args[1], "fastgrow");
								sender.sendMessage(prefix + ChatColor.GREEN + "Fastgrow enabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Fastgrow enabled for you by: " + sender.getName());
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
				return true;
			}
		}
		return true;
	}
}
