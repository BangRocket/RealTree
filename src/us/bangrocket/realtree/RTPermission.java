package us.bangrocket.realtree;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

// This is here in the off chance that I need to implement some other random-ass Permissions plugin. 
// Also, this is where I handle LivingForest's user file. I hate this file, but I'm forcing myself to implement
// the functionality so I have full backward compatability. 

// The functionality for my own non-permissions user solution is/will be found in the RTConfig class.

public class RTPermission
{
	public static RealTree plugin; 
	
	public RTPermission(RealTree instance)
	{
        plugin = instance;
	}
	
	public boolean userFileLoaded = false;
	public boolean usingPermissions = true;
	
	private Configuration users = null;
	
	public void checkPermissionsManager(Server server)
	{		
		Plugin permPlugin = server.getPluginManager().getPlugin("PermissionsBukkit");

		if (permPlugin != null)
		{
			plugin.output("Permission plugin found: PermissionsBukkit");
			this.usingPermissions = true;
		}
		else
		{
			plugin.output("Permission system not detected, defaulting to OP");
			this.usingPermissions = false;
		}
   }
	
	public void checkUserConfig()
	  {
			File userFile = new File(plugin.getDataFolder() + File.separator + "users.yml");
			
			if ((userFile.exists()) && (!usingPermissions))
			{
				plugin.output("LivingForest users.yml file found!");
				users = new Configuration(new File(plugin.getDataFolder(), "users.yml"));
				users.load();	
				userFileLoaded = true;
			}
			else
			{
				//plugin.output("No File!");
				userFileLoaded = false;
			}
	  }

	public void toggleLFPlayer(String name)
	{
		if (this.userFileLoaded)
		{
			if (users.getBoolean("User." + name + ".CanUse", false))
			{
				users.setProperty("User." + name + "CanUse", false);
			}
			else
			{
				users.setProperty("User." + name + "CanUse", true);
			}

			if (!this.isPlayerLFUser(name))
				users.setProperty("User." + name + ".Storedname", name);
			users.save();
		}
	}

	public void disableLFPlayer(String name)
	{
		if (this.userFileLoaded)
		{
			users.setProperty("User." + name + ".CanUse", false);
			if (!this.isPlayerLFUser(name))
				users.setProperty("User." + name + ".Storedname", name);
			users.save();
		}
	}

	public void enableLFPlayer(String name)
	{
		if (this.userFileLoaded)
		{
			users.setProperty("User." + name + ".CanUse", true);
			if (!this.isPlayerLFUser(name))
				users.setProperty("User." + name + ".Storedname", name);
			users.save();
		}
	}

	public boolean isPlayerLFUser(String playername)
	{		
		if (this.userFileLoaded)
		{
			if (users.getBoolean("User." + playername + ".CanUse", false))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isUserAllowed(String name)
	{
		if (this.userFileLoaded)
		{
			return users.getBoolean("User." + name + ".CanUse", false);
		}
		return false;
	}
}

// player.hasPermission(String);
//(!this.users.getPlayerCanUse(event.getPlayer().getName()))