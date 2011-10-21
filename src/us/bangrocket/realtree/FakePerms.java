package us.bangrocket.realtree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.bukkit.util.config.Configuration;

public class FakePerms
{
	public static RealTree plugin; 

	public FakePerms(RealTree instance)
	{
		plugin = instance;
	}
	
	Configuration userPerms;
	boolean userFileLoaded = false;

	public void moveUserFile(){
		plugin.getDataFolder().mkdir();
		plugin.getDataFolder().setWritable(true);
		plugin.getDataFolder().setExecutable(true);
		extractFile("users.dat");
	}

	private void extractFile(String name)
	{
		File actual = new File(plugin.getDataFolder(), name);
		if (!actual.exists())
		{
			InputStream input = getClass().getResourceAsStream("/" + name);
			if (input != null) 
			{
				FileOutputStream output = null;
				try
				{
					output = new FileOutputStream(actual);
					byte[] buf = new byte[8192];
					int length = 0;

					while ((length = input.read(buf)) > 0)
					{
						output.write(buf, 0, length);
					}

					plugin.output("Default file written: " + name);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if (input != null)
							input.close();
					}
					catch (Exception e)
					{
					}
					try
					{
						if (output != null)
							output.close();
					}
					catch (Exception e)
					{
					}
				}
			}
		}
	}

	  public void readUserFile()
	  {
		//error checking?
		userPerms = new Configuration(new File(plugin.getDataFolder(), "users.dat"));
		userPerms.load();
		userFileLoaded = true;
	  }

	  public Configuration getUserPerms()
	  {
		  return userPerms;
	  }
	  
	  public boolean hasFakePerms(String name, String permission)
	  {
			if (userFileLoaded)
			{
				plugin.output("Looking for permission: " + permission + " for player: " + name +". Result: " + Boolean.toString(userPerms.getBoolean("users." + name + ".permissions." + permission, false)));
				//plugin.output("users." + name + ".permissions." + permission);
				//plugin.output(Boolean.toString(userPerms.getBoolean("users." + name + ".permissions." + permission, false)));
				return userPerms.getBoolean("users." + name + ".permissions." + permission, false);
			}
			
			if (!plugin.getConfig().getUseFakePerms())
			{
				return plugin.getServer().getPlayer(name).isOp();
			}
			
		  return false;
	  }
	  
	  public boolean giveFakePerms(String name, String permission)
	  {
		  if (userFileLoaded)
		  {
			 userPerms.setProperty("users." + name + ".permissions." + permission, true);
			 userPerms.save();
		  }
		  return false;
	  }
	  
	  public boolean takeFakePerms(String name, String permission)
	  {
		  if (userFileLoaded)
		  {
			 userPerms.setProperty("users." + name + ".permissions." + permission, false);
			 userPerms.save();
		  }
		  return false;
	  }
	  
}
