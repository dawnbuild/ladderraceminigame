package dawn.eventminigame.Listener;

import dawn.eventminigame.Area;
import dawn.eventminigame.Eventminigame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class EditorModeListener implements Listener {
    public static Map<Player, Area> editorMode = new HashMap<>();

    @EventHandler
    public void WhenRightClickLeftClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        if (editorMode.containsKey(player)){
            switch (e.getAction()){
                case LEFT_CLICK_BLOCK:
                    player.sendMessage(Eventminigame.ChatColor("&c&lPOS 1 SET &8[&c!&8]"));
                    editorMode.get(player).setLoc1(e.getClickedBlock().getLocation());
                    e.setCancelled(true);
                    break;
                case RIGHT_CLICK_BLOCK:
                    player.sendMessage(Eventminigame.ChatColor("&c&lPOS 2 SET &8[&c!&8]"));
                    editorMode.get(player).setLoc2(e.getClickedBlock().getLocation());
                    e.setCancelled(true);
                    break;
            }
        }
    }
}
