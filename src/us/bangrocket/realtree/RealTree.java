package us.bangrocket.realtree;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

import java.util.LinkedList;
import java.util.logging.Logger;

public class RealTree extends JavaPlugin{
	
	// I would say that 50% of this code is lifted directly from Hawox's Autoplant. I filled in the blanks when it came to
	// emulating the LivingForest functionality, but this was a pretty solid base to begin from. Places where I've deviated
	// from his code will be marked in comments.
	
	

	Logger _log = Logger.getLogger("Minecraft");
	
	PluginDescriptionFile pdfFile;
	
	private RTConfig _config =  new RTConfig(this);
	private RTPermission _permission = new RTPermission(this);
		
	private RTBlockListener _blockListener = new RTBlockListener(this);
	private RTPlayerListener _playerListener = new RTPlayerListener(this);
	
	private RTFastgrow _fgListener = new RTFastgrow(this);
	private RTOvergrow _RTOverGrow = new RTOvergrow(this);
	
	private RTNewCommand _commander = new RTNewCommand(this);
	private RTTaskMan _taskman = new RTTaskMan(this);
	
	//saplings awaiting planting
	private LinkedList<BlockState> replant = new LinkedList<BlockState>();
	
	//protected saplings awaiting unprotecting
	private LinkedList<Block> protect = new LinkedList<Block>();
		
	public void onEnable()
	{ 

		PluginManager pm = this.getServer().getPluginManager();

		pdfFile = getDescription();

		//load up our files if they don't exist
		_config.moveFiles("config.yml");
		_config.moveFiles("users.dat");

		// setup configuration file
		_config.readConfig();

		// check for permssions
		_permission.checkPermissionsManager(getServer());
		
		// check for user file
		_permission.checkUserConfig();

		
		//Block registers
		pm.registerEvent(Event.Type.BLOCK_BREAK, this._blockListener, Event.Priority.High, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this.getFastgrow(), Event.Priority.High, this);
		pm.registerEvent(Event.Type.BLOCK_BURN, this._blockListener, Event.Priority.High, this);
		
				
		//Player registers
		pm.registerEvent(Event.Type.PLAYER_JOIN, this._playerListener, Event.Priority.High, this);

		//TODO: Future Implementation of cycle growth
		//TODO: Check to make sure that only one plant type is going at once.
		//if (_config.getcycleReplant())
		//{
		//	_taskman.startCycleReplantTask();
		//}
		
		this.output("version " + pdfFile.getVersion() + " has been loaded.");
		
	}
	 
	public void onDisable()
	{ 
		
		//if (_config.getcycleReplant())
		//{
		//	_taskman.stopCycleReplantTask();
		//}
		
		this.output("version " + pdfFile.getVersion() + " has been unloaded.");
	}
  
  	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  	{
  		 return _commander.processCommand(sender, cmd, commandLabel, args);
  	}
	
	//these two functions share the Replant/Protect lists with other classes
	public LinkedList<BlockState> getReplant()
	{
		return replant;
	}

	public LinkedList<Block> getProtect()
	{
		return protect;
	}


	public RTTaskMan getTaskMan()
	{
		return this._taskman;
	}
	
	public RTPermission getPermMan()
	{
		return this._permission;
	}
	
	public RTConfig getConfig()
	{
		return this._config;
	}
	
	public RTOvergrow getOvergrow()
	{
		return this._RTOverGrow;
	}

	public RTFastgrow getFastgrow()
	{
		return _fgListener;
	}

	public void setFastgrow(RTFastgrow _fgListener) {
		this._fgListener = _fgListener;
	}

	//output [RealTree] formated text to console
	public void output(String output)
	{
		this._log.info("[" + pdfFile.getName() + "] " + output);
	}
	
	//output [RealTree] formated text to player
	public void output(String output, Player player)
	{
		player.sendMessage("[" + pdfFile.getName() + "] " + output);
	}
	
	//output [RealTree] formated text to console with color
	public void output(String output, Player player, ChatColor color)
	{
		player.sendMessage("[" + pdfFile.getName() + "] "+ color + output);
	}
	
	public void output(String output, CommandSender sender)
	{
		sender.sendMessage("[" + pdfFile.getName() + "] " + output);
	}
	
	public void output(String output, CommandSender sender, ChatColor color)
	{
		sender.sendMessage("[" + pdfFile.getName() + "] " + color + output);
	}
	
	//function to quickly return XYZ location of a block (for debugging purposes)
	public String getXYZ(Block block)
	{
		return Double.toString(block.getX()) + ", " + Double.toString(block.getY()) + ", " + Double.toString(block.getZ()) ;
	}


	
}

