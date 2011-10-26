package us.bangrocket.realtree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class RTCommand {

	public static RealTree plugin; 
	
	public RTCommand(RealTree instance)
	{
		plugin = instance;
	}
	
	private String[] permissions = { 
			"realtree.toggle.plugin",	
			"realtree.toggle.self", 
			"realtree.toggle.all", 
			"realtree.reload.users", 
			"realtree.reload.config", 
			"realtree.config.varichange", 
			"realtree.config.fakeperm", 
			"realtree.perms"
			};
	
	private List<String> permissionlist = new ArrayList<String>(permissions.length);
	

	public boolean processCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		
		String prefix = "[" + plugin.pdfFile.getName() + "] ";
		
        for (String s : permissions)
        {  
        	permissionlist.add(s);  
        }  
        
		if (plugin.getPermMan().isPermissionsPluginEnabled())
		{
			//RealTree Commands
			if ((cmd.getName().equalsIgnoreCase("rt")) || (cmd.getName().equalsIgnoreCase("realtree")))
			{
				//
				//SINGLE ITEM COMMMANDS (ie /realtree enable and /realtree reload)
				//
				if (args.length >= 1)
				{
					if(args[0].equalsIgnoreCase("test")) 
					{
						if ((sender.hasPermission("realtree.test")))
						{
							plugin.output(ChatColor.AQUA + "Whammy-whoozle!", sender);
						}
						return true;
					}

					if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("e")))
					{
						if ((sender.hasPermission("realtree.toggle.plugin")))// || (sender.isOp()))
						{
							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled globally!");
							if (!(plugin.getConfig().isRTEnabled()))
								plugin.getConfig().setRTEnabled(true);
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
					{

						if ((sender.hasPermission("realtree.toggle.plugin")))// || (sender.isOp()))
						{
							sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled globally!");
							if ((plugin.getConfig().isRTEnabled()))
								plugin.getConfig().setRTEnabled(false);
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("reload"))  || (args[0].equalsIgnoreCase("r")))  
					{
						if ((sender.hasPermission("realtree.config.reload")))// || sender.isOp())
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
							//plugin.getPermMan().readUserFile();
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("overgrow"))  || (args[0].equalsIgnoreCase("og")))  
					{
						if ((sender.hasPermission("realtree.toggle.self")))// || sender.isOp())
						{
							plugin.getPermMan().togglePlayer(sender.getName(), "overgrow");
							return true;
//							if (plugin.getPermMan().isUserAllowed(sender.getName(),"overgrow"))
//							{
//								plugin.output("test", sender, ChatColor.GREEN);//disable
//							}
//							else
//							{
//								plugin.output("test", sender, ChatColor.RED);//enable
//							}
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("fastgrow"))  || (args[0].equalsIgnoreCase("fg")))  
					{
						if ((sender.hasPermission("realtree.toggle.self"))) //|| sender.isOp())
						{
							if (sender.hasPermission("realtree.fastgrow"))
							{
								sender.getServer().getPluginManager().removePermission("realtree.fastgrow");
								sender.sendMessage(prefix + ChatColor.GRAY + "Overgrow disabled for player: " + sender.getName());						
							}
							else
							{
								Permission p = new Permission("realtree.fastgrow");
								plugin.getServer().getPluginManager().addPermission(p);
								sender.sendMessage(prefix + ChatColor.GRAY + "Overgrow enabled for player: " + sender.getName());	
							}
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("replant"))  || (args[0].equalsIgnoreCase("rp")))  
					{
						if ((sender.hasPermission("realtree.toggle.self")))// || sender.isOp())
						{
							if ((sender.hasPermission("realtree.replant")))
							{
								sender.getServer().getPluginManager().removePermission("realtree.replant");
								sender.sendMessage(prefix + ChatColor.GRAY + "Replant disabled for player: " + sender.getName());						
							}
							else
							{

								Permission p = new Permission("realtree.replant");
								plugin.getServer().getPluginManager().addPermission(p);
								sender.sendMessage(prefix + ChatColor.GRAY + "Replant enabled for player: " + sender.getName());	
							}
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
				}
				else if (args.length == 2)
					//
					//DOUBLE ITEM COMMMANDS (ie /realtree enable me and /realtree reload users)
					//
				{
					if ((args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
					{
						if ((args[1].equalsIgnoreCase("me")) || (args[1].equalsIgnoreCase("m")))
						{	
							if ((sender.hasPermission("realtree.toggle.self")))// || (sender.isOp()))
							{
								sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled!");
								//							if ((plugin.getConfig().isRTEnabled()))
								//								plugin.getConfig().setRTEnabled(false);
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("e")))
					{
						if ((args[1].equalsIgnoreCase("me")) || (args[1].equalsIgnoreCase("m")))
						{	
							if ((sender.hasPermission("realtree.toggle.self")))// || (sender.isOp()))
							{
								//							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled!");
								//							if (!(plugin.getConfig().isRTEnabled()))
								//								plugin.getConfig().setRTEnabled(true);
								return true;
							}
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

				}
				else if (args.length == 3)
					//
					//TRIPLE ITEM COMMMANDS (ie /realtree varchange <variable> <new value>)
					//	
				{
					if ((args[0].equalsIgnoreCase("addPerm")))
					{
						if ((sender.hasPermission("realtree.test")))
						{
							if ((args[1].isEmpty()) || (args[2].isEmpty()))
							{
								sender.sendMessage("/realtree addPerm <playername> <validpermission>");
								return false;
							}

							if (!(permissionlist.contains(args[2])))
							{
								sender.sendMessage(ChatColor.RED + "Invalid permission!");
								return false;					
							}

							//plugin.getFakePerms().giveFakePerms(args[1], args[2]);
							return true;
						}
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
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
		}
		else //if we're not using permissions, then our command list is different
		{
			if ((cmd.getName().equalsIgnoreCase("rt")) || (cmd.getName().equalsIgnoreCase("realtree")))
			{
				//
				//SINGLE ITEM COMMMANDS (ie /realtree enable and /realtree reload)
				//
				if (args.length == 1)
				{
					plugin.output(Integer.toString(args.length));
					if(args[0].equalsIgnoreCase("test")) 
					{
						//if ((sender.hasPermission("realtree.test")))
						//{
							plugin.output(ChatColor.AQUA + "Whammy-whoozle!", sender);
						//}
						return true;
					}

					if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("e")))
					{
						if (sender.isOp())
						{
							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled globally!");
							if (!(plugin.getConfig().isRTEnabled()))
								plugin.getConfig().setRTEnabled(true);
							return true;
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("disable"))  || (args[0].equalsIgnoreCase("d")))  
					{

						if (sender.isOp())
						{
							sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled globally!");
							if ((plugin.getConfig().isRTEnabled()))
								plugin.getConfig().setRTEnabled(false);
							return true;
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}

					if ((args[0].equalsIgnoreCase("reload"))  || (args[0].equalsIgnoreCase("r")))  
					{
						if (sender.isOp())
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
							return true;
						}
					}

					if ((args[0].equalsIgnoreCase("replant"))  || (args[0].equalsIgnoreCase("rp")))  
					{
						if (sender.isOp())
						{
							if (plugin.getPermMan().isUserAllowed(sender.getName()))
							{
								plugin.getPermMan().disablePlayer(sender.getName());
								sender.sendMessage(prefix + ChatColor.RED + "Replant disabled for you.");
							}
							else
							{
								plugin.getPermMan().enablePlayer(sender.getName());
								sender.sendMessage(prefix + ChatColor.GREEN + "Replant enabled for you.");	
							}
							return true;
						}
					}
				}
			}
			
			if (args.length == 2)
				//
				//DOUBLE ITEM COMMMANDS (ie /realtree enable me and /realtree reload users)
				//
			{
				if ((args[0].equalsIgnoreCase("replant"))  || (args[0].equalsIgnoreCase("rp")))  
				{
					if (plugin.getPermMan().isPlayerUser(args[1]))
					{	
						if (sender.isOp())
						{
							if (plugin.getPermMan().isUserAllowed(sender.getName()))
							{								
								plugin.getPermMan().disablePlayer(args[1]);
								sender.sendMessage(prefix + ChatColor.RED + "Realtree disabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Realtree disabled for you by: " + sender.getName());
								return true;
							}
							else
							{
								plugin.getPermMan().enablePlayer(args[1]);
								sender.sendMessage(prefix + ChatColor.GREEN + "Realtree enabled for player: " + args[1]);
								plugin.getServer().getPlayer(args[1]).sendMessage(prefix + "Realtree enabled for you by: " + sender.getName());
								return true;
							}
						}
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
						return true;
					}
					sender.sendMessage(prefix + ChatColor.RED + "Player not found in user database!");
					return true;
				}
			}
		}
		
		//LivingForest Commands (I may someday re-implement these)
//		if (((cmd.getName().equalsIgnoreCase("lf")) || (cmd.getName().equalsIgnoreCase("livingforest"))) &&	plugin.getConfig().isFGEnabled())
//		{
//			if (args.length == 1)
//			{
//				if(args[0].equals("test")) 
//				{
//					sender.sendMessage(prefix + ChatColor.GRAY + "Invalid Living Forest Command.");
//					return true;
//				}
//			}
//			else if (args.length == 2)
//			{
//				if ((args[0].equalsIgnoreCase("g")) || (args[0].equalsIgnoreCase("global")))
//				{
//					if ((args[1].equalsIgnoreCase("r")) || (args[1].equalsIgnoreCase("reload")))
//					{
//						if ((sender.hasPermission("livingforest.chat.global.reload")) || sender.isOp())
//						{
//							if (plugin.getConfig().isLFConfig())
//							{
//								sender.sendMessage(prefix + ChatColor.GRAY + "LivingForest config reloaded.");
//							}
//							else
//							{
//								sender.sendMessage(prefix + ChatColor.GRAY + "RealTree config reloaded.");
//							}
//							plugin.getConfig().readConfig();
//							return true;
//						}
//						else
//						{
//							sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//							return true;
//						}
//					}
//				
//					if ((args[1].equalsIgnoreCase("d")) || (args[1].equalsIgnoreCase("disable")))
//					{
//						if ((sender.hasPermission("livingforest.chat.global.disable")) || (sender.isOp()))
//						{
//							sender.sendMessage(prefix + ChatColor.RED + "RealTree plugin disabled!");
//						
//							plugin.getConfig().setRTEnabled(false);
//							return true;
//						}
//						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//						return true;	
//					}
//				
//					if ((args[1].equalsIgnoreCase("e")) || (args[1].equalsIgnoreCase("enable")))
//					{
//						if ((sender.hasPermission("livingforest.chat.global.enable")) || (sender.isOp()))
//						{
//							sender.sendMessage(prefix + ChatColor.GREEN + "RealTree plugin enabled!");
//							plugin.getConfig().setRTEnabled(true);
//							return true;
//						}
//						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//						return true;
//					}
//				}		
//			
//				if ((args[0].equalsIgnoreCase("m")) || (args[0].equalsIgnoreCase("me")))
//				{
//					if ((args[1].equalsIgnoreCase("d")) || (args[1].equalsIgnoreCase("disable")))
//					{
//						if ((sender.hasPermission("livingforest.chat.me.disable")) || (sender.isOp()))
//						{
//							plugin.getPermMan().disableLFPlayer(sender.getName());
//							sender.sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for you!");
//							return true;
//						}
//						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//						return true;
//					}
//
//					if ((args[1].equalsIgnoreCase("e")) || (args[1].equalsIgnoreCase("enable")))
//					{
//						if ((sender.hasPermission("livingforest.chat.me.enable")) || (sender.isOp()))
//						{
//							plugin.getPermMan().enableLFPlayer(sender.getName());
//							sender.sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for you!");
//							return true;
//						}
//						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//						return true;
//					}
//				}
//			}
//			else
//			{
//				if (args.length == 3)
//				{
//					if ((args[0].equalsIgnoreCase("p")) || (args[0].equalsIgnoreCase("player")))
//					{
//						if (args[1].length() >= 1)
//						{
//							if (plugin.getPermMan().isPlayerLFUser(args[1]))
//							{
//								if ((args[2].equalsIgnoreCase("d")) || (args[2].equalsIgnoreCase("disable")))
//								{
//									if ((sender.hasPermission("livingforest.chat.player.disable")) || (sender.isOp()))
//									{
//										plugin.getPermMan().disableLFPlayer(args[1]);
//										sender.sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for player: " + args[1]);
//										plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GRAY + "Realtree disabled for you by: " + sender.getName());
//										return true;
//									}
//									sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
//									return true;
//								}
//								
//								if ((args[2].equalsIgnoreCase("e")) || (args[2].equalsIgnoreCase("enable")))
//								{
//									if ((sender.hasPermission("livingforest.chat.player.enable")) || (sender.isOp()))
//									{
//										plugin.getPermMan().enableLFPlayer(args[1]);
//										sender.sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for player: " + args[1]);
//										plugin.getServer().getPlayer(args[1]).sendMessage(prefix + ChatColor.GRAY + "Realtree enabled for you by: " + sender.getName());
//										return true;
//									}
//									sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to use this command!");
//									return true;
//								}
//							}
//							else
//							{
//								if (plugin.getPermMan().userFileLoaded)
//								{
//									sender.sendMessage(ChatColor.GRAY + "Player not found in user database!");
//								}
//								else
//								{
//									sender.sendMessage(prefix + ChatColor.RED + "LivingForest user database not found!");
//								}
//								return true;
//							}
//						}
//					}
//				}
//				else					
//				{
//					if ((cmd.getName().equalsIgnoreCase("lf")) || (cmd.getName().equalsIgnoreCase("livingforest"))) 
//					{
//						sender.sendMessage(prefix + ChatColor.RED + "Invalid LivingForest command!");
//					}
//					return true;
//				}
//			}
//		}
		//sender.sendMessage(prefix + ChatColor.RED + "LivingForest commands are disabled, please use RealTree commands.");
	return false;
	}
}
