package us.bangrocket.realtree;

import java.util.logging.Logger;

//import org.bukkit.GameMode;
//import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class RTPlayerListener extends PlayerListener
{

	Logger _log = Logger.getLogger("Minecraft");
	
	public static RealTree plugin; 
	
	public RTPlayerListener(RealTree instance)
	{
        plugin = instance;
	}
	
	public void onPlayerJoin (PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		if (!(plugin.getPermMan().isPlayerUser(player.getName())))
			plugin.getPermMan().enablePlayer(player.getName());
	}
	
//	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event)
//	{
//		if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
//		{
//			plugin.getPermMan().disableLFPlayer(event.getPlayer().getName());
//		}
//		else
//		{
//			plugin.getPermMan().enableLFPlayer(event.getPlayer().getName());
//		}
//	}

}