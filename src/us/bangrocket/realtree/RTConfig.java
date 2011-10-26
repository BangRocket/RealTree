package us.bangrocket.realtree;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.bukkit.util.config.Configuration;

public class RTConfig
{
	
	public static RealTree plugin; 
	
	public RTConfig(RealTree instance)
	{
        plugin = instance;
	}
	
	//config variables, set to default
	private String textPlanted = "A new sapling will arrive in one moment";
	private String textProtected = "This sapling is protected for a while."; //matching LF wording
	private int delayTime = 15; //time in seconds until a new sapling will be planted
	private int protectTime = 3 ; //time in seconds a sapling will be protected
	
	//Implement this later
	//private int delayTimeBurn = 10; //time in seconds until a new sapling will be planted after a burn
	
	private boolean RTEnabled = true;
	
	private boolean useFakePerms = true;
	
	private boolean tellUserPlanted = false;
	private boolean tellUserProtected = true;
	private boolean replantOnBurn = true;
	private boolean protectSaplings =  true;

	private boolean cycleReplant = false;
	private boolean creativeMode = false;
	private boolean protectBeforeSap = false;
	
	private boolean isLFConfig = false;
	
	//Fastgrow variables
	private boolean FGEnabled = true;
	private boolean FGDropFailSap = false;
	
	private int FGBasetime = 25;
	private int FGRandtime = 30;
	private int FGBigTree = 40;
	private int FGRedwood = 40;
	
	//Overgrow variables
	private boolean OGEnabled = true;
	private boolean OGChopped = true;
	private boolean OGBurned = true;
	
	private int OGExtraTrees = 3;
	private int OGminRadius = 3;
	private int OGmaxRadius = 8;

	public void moveFiles(String filename)
	{
		plugin.getDataFolder().mkdir();
		plugin.getDataFolder().setWritable(true);
		plugin.getDataFolder().setExecutable(true);
		//extractFile("config.yml");
		extractFile(filename);
	}

	//Taken and modified from iCon (and then taken and modified from hawox's treeplanter)
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

  public void readConfig()
  {
	Configuration config = new Configuration(new File(plugin.getDataFolder(), "config.yml"));
	config.load();
	
	if (config.getInt("Config.Version", 0) == 0) //This... is dirty but it works.
	{
		isLFConfig = false;
		
		plugin.output("Loading configuration from a RealTree config.yml");	
		
		tellUserPlanted = config.getBoolean("Tell_User_Planting", tellUserPlanted);
		tellUserProtected = config.getBoolean("Tell_User_Protected", tellUserProtected);
		
		replantOnBurn = config.getBoolean("Replant_Burned_Tree", replantOnBurn);

		protectSaplings = config.getBoolean("Protect_Saplings", protectSaplings);
		
		textPlanted = config.getString("Being_Planted_Text", textPlanted);
		textProtected = config.getString("Protected_Text", textProtected);

		delayTime = config.getInt("Plant_Delay", delayTime);
		protectTime = config.getInt("Protect_Time", protectTime);
		
		creativeMode = config.getBoolean("Creative_Mode", creativeMode);
		useFakePerms = config.getBoolean("UseFakePerms", useFakePerms);
		protectBeforeSap = config.getBoolean("Protect_Before_Replant", protectBeforeSap);
		
		//Fastgrow variables
		FGEnabled = config.getBoolean("Fastgrow_Enabled", FGEnabled);
		FGDropFailSap = config.getBoolean("Fastgrow_DropFailedTrees",FGDropFailSap);
		
		FGBasetime = config.getInt("Fastgrow_Basetime", FGBasetime);
		FGRandtime = config.getInt("Fastgrow_Randomtime", FGRandtime);
		FGBigTree = config.getInt("Fastgrow_BigTree", FGBigTree);
		FGRedwood = config.getInt("Fastgrow_Redwood", FGRedwood);
		
		//Overgrow variables
		OGEnabled = config.getBoolean("Overgrow_Enabled", OGEnabled);
		OGChopped = config.getBoolean("Overgrow_Chopped_Trees", OGChopped);
		OGBurned = config.getBoolean("Overgrow_Burned_Trees", OGBurned);
		
		setOGExtraTrees(config.getInt("Extra_Trees", getOGExtraTrees()));
		setOGminRadius(config.getInt("Min_Growth_Radius", getOGminRadius()));
		setOGmaxRadius(config.getInt("Max_Growth_Radius", getOGmaxRadius()));
		
	}
	else
	{
		
		isLFConfig = true;
		
		plugin.output("Loading configuration from a LivingTree v12 config.yml");
		
		tellUserPlanted = config.getBoolean("Living.Replant.TellUserPlanting", tellUserPlanted);
		tellUserProtected = config.getBoolean("Living.Replant.TellUserProtected", tellUserProtected);
		
		replantOnBurn = config.getBoolean("Living.Replant.ReplantBurnedTree", replantOnBurn);

		textPlanted = config.getString("Living.Replant.PlantingMessage", textPlanted);
		textProtected = config.getString("Living.Replant.ProtectMessage", textProtected);

		delayTime = config.getInt("Living.Replant.Delay", delayTime);
		protectTime = config.getInt("Living.Replant.ProtectTime", protectTime);

		//Fastgrow variables
		FGEnabled = config.getBoolean("Living.FastGrowEnabled", FGEnabled);
		FGDropFailSap = config.getBoolean("Living.Fastgrow.DropFailedTrees",FGDropFailSap);
		
		FGBasetime = config.getInt("Living.FastGrow.Basetime", FGBasetime);
		FGRandtime = config.getInt("Living.FastGrow.Randomtime", FGRandtime);
		FGBigTree = config.getInt("Living.FastGrow.ChanceOfBigTree", FGBigTree);
		FGRedwood = config.getInt("Living.FastGrow.ChanceOfRedWood", FGRedwood);
		
		//Overgrow variables
		OGEnabled = config.getBoolean("Living.Replant.OverGrowthMode", OGEnabled);
		OGChopped = config.getBoolean("Living.Replant.OverGrowth.OverGrowChoppedTrees", OGChopped);
		OGBurned = config.getBoolean("Living.Replant.OverGrowth.OverGrowBurnedTrees", OGBurned);
		
		setOGExtraTrees(config.getInt("Living.Replant.OverGrowth.NumberOfExtraTrees", getOGExtraTrees()));
		
		setOGminRadius(config.getInt("Living.Replant.OverGrowth.MinGrowthRadius", getOGminRadius()));
		setOGmaxRadius(config.getInt("Living.Replant.OverGrowth.MaxGrowthRadius", getOGmaxRadius()));
	}
}

//Returns string about sapling being planted
	public String getTellUserPlanted()
	{
		return textPlanted;
	}
	
	//Sets string about saplings being planted
	public void setTellUserPlanted(String tellUserPlanted)
	{
		textPlanted = tellUserPlanted;
	}

	//Returns string about sapling being protected
	public String getTellUserProtected()
	{
		return textProtected;
	}

	//sets string about sapling being planted
	public void setTellUserProtected(String tellUserProtected)
	{
		textProtected = tellUserProtected;
	}
	
	//Returns the time that sapling should be protected
	public int getProtectTime()
	{
		return protectTime;
	}

	//sets the time that sapling should be protected
	public void setProtectTime(int protectTime)
	{
		this.protectTime = protectTime;
	}

	//returns if users should be informed of saplings being planted
	public boolean isTellUserPlanted()
	{
		return tellUserPlanted;
	}
	
	//sets if users should be informed of saplings being planted
	public void setTellUserPlanted(boolean telluser)
	{
		tellUserPlanted = telluser;
	}
  
	//sets the delay time between chop and sapling plant
	public void setdelayTime(int delayTime)
	{
		this.delayTime = delayTime;
	}

	//returns the delay time between chop and sapling plant
	public int getdelayTime()
	{
		return delayTime;
	}

	//returns if we should be protecting new saplings
	public boolean isProtectEnabled() //Uwaaah~ Hawox code doesnt support this!
	{
		return protectSaplings;
	}
	
	//returns if we should tell the user about sapling protection
	public boolean isTelluserProtected()
	{
		return tellUserProtected;
	}

	//sets if we should tell the user about sapling protection
	public void setTelluserProtected(boolean telluserProtected)
	{
		tellUserProtected = telluserProtected;
	}

	//returns if we should replant on tree's burning down
	public boolean isReplantOnBurn()
	{
		return replantOnBurn;
	}

	//sets if we should replant on tree's burning down
	public void setReplantOnBurn(boolean replantOnBurn)
	{
		this.replantOnBurn = replantOnBurn;
	}
	
	//set if we want to use the day/night timer
	public void setcycleReplant(boolean cycleReplant)
	{
		this.cycleReplant = cycleReplant;
	}
	
	//returns if we want to use the day/night timer
	public boolean getcycleReplant()
	{
		return cycleReplant;
	}
	
	//set if we want to use the day/night timer
	public void setcreativeMode(boolean creativeMode)
	{
		this.creativeMode = creativeMode;
	}
	
	//returns if we want to use the day/night timer
	public boolean getcreativeMode()
	{
		return creativeMode;
	}
	
	public boolean isProtectBeforeSap()
	{
		
		return protectBeforeSap;
	}
	
	public void setProtectBeforeSap(boolean protectBeforeSap)
	{
		this.protectBeforeSap = protectBeforeSap;
	}
	
	public boolean isRTEnabled()
	{
		return RTEnabled;
	}
	
	public void setRTEnabled(boolean RTEnabled)
	{
		this.RTEnabled = RTEnabled;
	}
	
	public boolean isLFConfig()
	{
		return isLFConfig;
	}
	
	// Fastgrow config returns
	public void setFGEnabled(boolean FGEnabled)
	{
		this.FGEnabled = FGEnabled;
	}
	
	public boolean isFGEnabled()
	{
		return FGEnabled;
	}
	
	public void setFGDropFailSap(boolean FGDropFailSap)
	{
		this.FGDropFailSap = FGDropFailSap;
	}
	
	public boolean getFGDropFailSap()
	{
		return FGDropFailSap;
	}
	
	public void setFGBasetime(int FGBasetime)
	{
		this.FGBasetime = FGBasetime;
	}
	
	public int getFGBasetime()
	{
		return FGBasetime;
	}
	
	public void setFGRandtime(int FGRandtime)
	{
		this.FGRandtime = FGRandtime;
	}
	
	public int getFGRandtime()
	{
		return FGRandtime;
	}
	
	public void setFGBigTree(int FGBigTree)
	{
		this.FGBigTree = FGBigTree;
	}
	
	public int getFGBigTree()
	{
		return FGBigTree;
	}
	
	public void setFGRedwood(int FGRedwood)
	{
		this.FGRedwood = FGRedwood;
	}
	
	public int getFGRedwood()
	{
		return FGRedwood;
	}

	public int getOGminRadius() {
		return OGminRadius;
	}

	public void setOGminRadius(int oGminRadius) {
		OGminRadius = oGminRadius;
	}

	public int getOGmaxRadius() {
		return OGmaxRadius;
	}

	public void setOGmaxRadius(int oGmaxRadius) {
		OGmaxRadius = oGmaxRadius;
	}

	public int getOGExtraTrees() {
		return OGExtraTrees;
	}

	public void setOGExtraTrees(int oGExtraTrees) {
		OGExtraTrees = oGExtraTrees;
	}

	public boolean getUseFakePerms() {
		return useFakePerms;
	}

	public void setFakePerms(boolean useFakePerms)
	{
		this.useFakePerms = useFakePerms;
	}

	public boolean isOGEnabled() {
		return OGEnabled;
	}
}
