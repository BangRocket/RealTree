package us.bangrocket.realtree;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RTCommand {

	public static RealTree plugin; 
	
	public RTCommand(RealTree instance)
	{
        plugin = instance;
	}
		
	public boolean processCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		
		String prefix = "[" + plugin.pdfFile.getName() + "] ";
		
		//RealTree Commands
		
		if ((cmd.getName().equalsIgnoreCase("rt")) || (cmd.getName().equalsIgnoreCase("realtree")))
		{
			if (args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("test")) 
				{
					if ((sender.hasPermission("realtree.test")) || (plugin.getFakePerms().hasFakePerms(sender.getName(),"realtree.test")))
					{
						plugin.output("Whammy-whoozle!", sender);
					}
					return true;
				}

				if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("e")))
				{
					if ((sender.hasPermission("realtree.toggle")) || (sender.isOp()))
					{
						sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled!");
						if (!(plugin.getConfig().isRTEnabled()))
							plugin.getConfig().setRTEnabled(true);
						return true;
					}
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				}

				if ((args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
				{

					if ((sender.hasPermission("realtree.toggle")) || (sender.isOp()))
					{
						sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled!");
						if ((plugin.getConfig().isRTEnabled()))
							plugin.getConfig().setRTEnabled(false);
						return true;
					}
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
					return true;
				}
				
				if ((args[0].equalsIgnoreCase("reload"))  || (args[0].equalsIgnoreCase("r")))  
				{
					if ((sender.hasPermission("realtree.reload")) || sender.isOp())
					{
						if (plugin.getConfig().isLFConfig())
						{
							sender.sendMessage(prefix + ChatColor.GRAY + "LivingForest config reloaded.");
						}
						else
						{
							sender.sendMessage(prefix + ChatColor.GRAY + "RealTree config reloaded.");
						}
						plugin.getConfig().readConfig();
						return true;
					}
				}
				
				if ((args[0].equalsIgnoreCase("addPerm")))
				{
					if ((sender.hasPermission("realtree.test")) || (plugin.getFakePerms().hasFakePerms(sender.getName(),"realtree.test")))
						{
							//error checking please!
							plugin.getFakePerms().giveFakePerms(args[1], args[2]);
							return true;
						}
					return true;
				}	
			}
			else
			{
				if ((cmd.getName().equalsIgnoreCase("rt")) || (cmd.getName().equalsIgnoreCase("realtree")))
				{
					sender.sendMessage(ChatColor.RED + "Invalid RealTree command!");
				}			
				return false;
			}
		}
		
		
		//LivingForest Commands
		if ((cmd.getName().equalsIgnoreCase("lf")) || (cmd.getName().equalsIgnoreCase("livingforest")))
		{
			if (args.length == 1)
			{
				if(args[0].equals("test")) 
				{
					sender.sendMessage(prefix + ChatColor.GRAY + "Invalid Living Forest Command.");
					return true;
				}
			}
			else if (args.length == 2)
			{
				if ((args[0].equalsIgnoreCase("g")) || (args[0].equalsIgnoreCase("global")))
				{
					if ((args[1].equalsIgnoreCase("r")) || (args[1].equalsIgnoreCase("reload")))
					{
						if ((sender.hasPermission("livingforest.chat.global.reload")) || sender.isOp())
						{
							if (plugin.getConfig().isLFConfig())
							{
								sender.sendMessage(prefix + ChatColor.GRAY + "LivingForest config reloaded.");
							}
							else
							{
								sender.sendMessage(prefix + ChatColor.GRAY + "RealTree config reloaded.");
							}
							plugin.getConfig().readConfig();
							return true;
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
							return true;
						}
					}
				
					if ((args[1].equalsIgnoreCase("d")) || (args[1].equalsIgnoreCase("disable")))
					{
						if ((sender.hasPermission("livingforest.chat.global.disable")) || (sender.isOp()))
						{
							sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled!");
						
							plugin.getConfig().setRTEnabled(false);
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;	
					}
				
					if ((args[1].equalsIgnoreCase("e")) || (args[1].equalsIgnoreCase("enable")))
					{
						if ((sender.hasPermission("livingforest.chat.global.enable")) || (sender.isOp()))
						{
							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled!");
							plugin.getConfig().setRTEnabled(true);
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}		
			
				if ((args[0].equalsIgnoreCase("m")) || (args[0].equalsIgnoreCase("me")))
				{
					if ((args[1].equalsIgnoreCase("d")) || (args[1].equalsIgnoreCase("disable")))
					{
						if ((sender.hasPermission("livingforest.chat.me.disable")) || (sender.isOp()))
						{
							plugin.getPermMan().disableLFPlayer(sender.getName());
							sender.sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for you!");
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[1].equalsIgnoreCase("e")) || (args[1].equalsIgnoreCase("enable")))
					{
						if ((sender.hasPermission("livingforest.chat.me.enable")) || (sender.isOp()))
						{
							plugin.getPermMan().enableLFPlayer(sender.getName());
							sender.sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for you!");
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
			}
			else
			{
				if (args.length == 3)
				{
					if ((args[0].equalsIgnoreCase("p")) || (args[0].equalsIgnoreCase("player")))
					{
						if (args[1].length() >= 1)
						{
							if (plugin.getPermMan().isPlayerLFUser(args[1]))
							{
								if ((args[2].equalsIgnoreCase("d")) || (args[2].equalsIgnoreCase("disable")))
								{
									if ((sender.hasPermission("livingforest.chat.player.disable")) || (sender.isOp()))
									{
										plugin.getPermMan().disableLFPlayer(args[1]);
										sender.sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for player: " + args[1]);
										plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for you by: " + sender.getName());
										return true;
									}
									sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
									return true;
								}
								
								if ((args[2].equalsIgnoreCase("e")) || (args[2].equalsIgnoreCase("enable")))
								{
									if ((sender.hasPermission("livingforest.chat.player.enable")) || (sender.isOp()))
									{
										plugin.getPermMan().enableLFPlayer(args[1]);
										sender.sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for player: " + args[1]);
										plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for you by: " + sender.getName());
										return true;
									}
									sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
									return true;
								}
							}
							else
							{
								if (plugin.getPermMan().userFileLoaded)
								{
									sender.sendMessage(ChatColor.GRAY + "Player not found in user database!");
								}
								else
								{
									sender.sendMessage(prefix + ChatColor.RED + "LivingForest user database not found!");
								}
								return true;
							}
						}
					}
				}
				else					
				{
					if ((cmd.getName().equalsIgnoreCase("lf")) || (cmd.getName().equalsIgnoreCase("livingforest"))) 
					{
						sender.sendMessage(prefix + ChatColor.RED + "Invalid LivingForest command!");
					}
					return true;
				}
			}
		}
	return false;
	}
}
