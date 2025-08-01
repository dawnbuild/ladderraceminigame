package dawn.eventminigame.Listener;

import dawn.eventminigame.Commands.CmdVisaStartGame;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.GameState;
import dawn.eventminigame.Player.PlayerManager;
import dawn.eventminigame.Player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvent implements Listener {


    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (player.isOp()) return;
        if (PlayerManager.isPlayerPlaying(player)){
            PlayerManager.setPlayerState(player,PlayerState.LOSER);
        }
    }

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

    @EventHandler
    public void cancelPlayerDeath(PlayerDeathEvent e){
        Player player = e.getPlayer();
        if (player.isOp()) return;
        if (PlayerManager.isPlayerPlaying(player)){
            player.setHealth(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if (PlayerManager.isPlayerPlaying(player)){
            if (CmdVisaStartGame.ladderGame.getGameState().equals(GameState.START)){
                if (CmdVisaStartGame.ladderGame.getFinishArea().isPlayerInsideArea(player)){
                    PlayerManager.setPlayerState(player, PlayerState.WIN);
                }
            }
        }
    }

    @EventHandler
    public void disablePVP(EntityDamageByEntityEvent e){
        Player korban = (Player) e.getEntity();
        boolean config = Eventminigame.getInstance().getConfig().getBoolean("LadderRace.allowpvp");
        if (PlayerManager.isPlayerPlaying(korban)){
            if (config) return;
            e.setCancelled(true);
        }
    }

}
