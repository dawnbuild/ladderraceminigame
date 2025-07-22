package dawn.eventminigame.Listener;

import dawn.eventminigame.Player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerEvent implements Listener {

    @EventHandler
    public void playerBreakBlockEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        if (player.isOp()) return;
        if (PlayerManager.isPlayerPlaying(player)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerPlaceBlockEvent(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (player.isOp()) return;
        if (PlayerManager.isPlayerPlaying(player)){
            e.setCancelled(true);
        }
    }

}
