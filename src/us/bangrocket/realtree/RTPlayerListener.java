package us.bangrocket.realtree;

import java.util.logging.Logger;

import org.bukkit.event.player.PlayerListener;


public class RTPlayerListener extends PlayerListener
{

	Logger _log = Logger.getLogger("Minecraft");
	
	public static RealTree _plugin; 
	
	public RTPlayerListener(RealTree instance)
	{
        _plugin = instance;
	}

}