package us.bangrocket.realtree;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class RTPermission
{
	public static RealTree plugin; 
	
	public RTPermission(RealTree instance)
	{
        plugin = instance;
	}

	public boolean userFileLoaded = false;
	public boolean enabledPermissions =  false;
	public boolean usingPermissionsBukkit = false;
	
	private Configuration users = null;
	
	public void checkPermissionsManager(Server server)
	{		
		Plugin permPlugin = server.getPluginManager().getPlugin("PermissionsBukkit");

		if (permPlugin != null)
		{
			plugin.output("Permission plugin found: PermissionsBukkit");
			this.usingPermissionsBukkit = true;
			this.enabledPermissions = true;
		}
		else //elseif
		{
			plugin.output("Permission system not detected, defaulting to OPs");
			this.usingPermissionsBukkit = false;
		}
   }
	
	public boolean checkUserConfig()
	  {
			File userFile = new File(plugin.getDataFolder() + File.separator + "users.yml");
			File RTFile = new File(plugin.getDataFolder() + File.separator + "users.dat");
			
			if ((userFile.exists()))
			{
				plugin.output("LivingForest users.yml file loaded!");
				users = new Configuration(new File(plugin.getDataFolder(), "users.yml"));
				users.load();
				userFileLoaded = true;
			}
			else if ((RTFile.exists()))
			{
				plugin.output("RealTree users.dat file loaded!");
				users = new Configuration(new File(plugin.getDataFolder(), "users.dat"));
				users.load();
				userFileLoaded = true;
			}
			
			
			return userFileLoaded;
	  }

	public void togglePlayer(String name, String perm)
	{
		if (this.userFileLoaded)
		{
			if (users.getBoolean("RT." + name + "." + perm, false))
			{
				users.setProperty("User." + name + "." + perm, false);
			}
			else
			{
				users.setProperty("User." + name + "." + perm, true);
			}

			users.save();
		}
	}

	public void disablePlayer(String name)
	{
		if (this.userFileLoaded)
		{
			//users.setProperty("User." + name + ".CanUse", false);
			
			users.setProperty("RT." + name + ".RealTree", false);
			
			users.setProperty("RT." + name + ".overgrow", false);
			users.setProperty("RT." + name + ".fastgrow", false);
			users.setProperty("RT." + name + ".replant", false);

			users.save();
		}
	}

	public void enablePlayer(String name)
	{
		if (this.userFileLoaded)
		{
			plugin.output("Registering new player: " + name);
			
			//note the lower-case user. separates RT and LF functionality in the userfile
			users.setProperty("RT." + name + ".registered", true);
			
			if (plugin.getConfig().isProtectEnabled())
			{
				users.setProperty("RT." + name + ".replant", true);
			}
			else
			{
				users.setProperty("RT." + name + ".replant", false);
			}
			
			if (plugin.getConfig().isOGEnabled())
			{
				users.setProperty("RT." + name + ".overgrow", true);
			}
			else
			{
				users.setProperty("RT." + name + ".overgrow", false);
			}
			
			if (plugin.getConfig().isFGEnabled())
			{
				users.setProperty("RT." + name + ".fastgrow", true);
			}
			else
			{
				users.setProperty("RT." + name + ".fastgrow", false);
			}

			if (users.getBoolean("User." + name + ".CanUse", false))
			{
				users.setProperty("RT." + name + ".RealTree", true);
			}
			else if (plugin.getConfig().isRTEnabled())
			{
				users.setProperty("RT." + name + ".RealTree", true);
				//lets make sure we maintain this line from LF. It should never come again, though
				users.setProperty("User." + name + ".CanUse", true);
			}
			else
			{
				users.setProperty("RT." + name + ".RealTree", false);
			}
			
			users.save();
		}
	}

	public boolean isPlayerUser(String playername)
	{	
		boolean tempBool = false;
		
		if (this.userFileLoaded)
		{
			tempBool = users.getBoolean("RT." + playername + ".registered", false);
		}
		
		return tempBool;
	}
	
	public void disablePerm(String name, String perm)
	{
		if (this.userFileLoaded)
		{
			users.setProperty("RT." + name + "." + perm, false);

			users.save();
		}
	}
	
	public void enablePerm(String name, String perm)
	{
		if (this.userFileLoaded)
		{
			
			users.setProperty("RT." + name + "." + perm, true);

			users.save();
		}
	}


	public boolean isUserAllowed(String name)
	{
		if (this.userFileLoaded)
		{
			return users.getBoolean("RT." + name + ".RealTree", false);
		}
		return false;
	}

	public boolean isUserAllowed(String name, String perm)
	{
		if (this.userFileLoaded)
		{
			return users.getBoolean("RT." + name + "." + perm, false);
		}
		return false;
	}
	

	public boolean isPermissionsPluginEnabled()
	{
		return enabledPermissions;
	}
}
