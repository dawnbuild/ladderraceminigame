package dawn.eventminigame.Player;

import dawn.eventminigame.Commands.CmdVisaStartGame;
import dawn.eventminigame.ConfigManager;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.MiniGames.LadderGame;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    public static Map<Player,PlayerState> playerManager = new HashMap<>();
    public static Map<Player, Location> playerOriLocation = new HashMap<>();



    public static List<String> winners = new ArrayList<>();
    public static void setPlayerState(Player player, PlayerState playerState){
        playerManager.remove(player);
        switch (playerState){
            case PLAYING:
                playerOriLocation.put(player,player.getLocation());
                ConfigManager.sendPlayerMessage("playerstateplaying",player);
                playerManager.put(player,PlayerState.PLAYING);
                Eventminigame.changePlayerGamemode(player,GameMode.SURVIVAL);
                break;
            case WIN:
                ScoreboardManager.restorePlayerScoreboard(player);
                player.sendTitle(Eventminigame.ChatColor("&eYOU WIN!!!"),"");
                Eventminigame.playSoundToAllPlayer(Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST);

                ConfigManager.sendBroadcastMessage("playerstatewin",player);
                ConfigManager.sendPlayerMessage("playerstatewin",player);

                playerManager.put(player,PlayerState.WIN);
                LadderGame.totalWinner+=1;
                winners.add(player.getName());
                Eventminigame.changePlayerGamemode(player, GameMode.SPECTATOR);
                break;
            case LOSER:
                ScoreboardManager.restorePlayerScoreboard(player);
                player.sendTitle(Eventminigame.ChatColor("&cYOU LOSE!!!"),"");
                ConfigManager.sendPlayerMessage("playerstatelose",player);
                player.teleport(playerOriLocation.get(player));
                playerOriLocation.remove(player);
                break;
        }
    }

    public static void clearAllPlayer(){
        for (Player player : playerManager.keySet()){
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().remove(Material.STICK);
            if (PlayerManager.playerManager.get(player)==PlayerState.PLAYING){
                PlayerManager.setPlayerState(player,PlayerState.LOSER);
                continue;
            }
            if (playerManager.get(player).equals(PlayerState.WIN)){
                player.teleport(winnerNextWorld());
                playerManager.remove(player);
                continue;
            }
            player.teleport(playerOriLocation.get(player));
        }
        ScoreboardManager.sb.clear();
    }

    public static Location winnerNextWorld(){
        return Bukkit.getWorld(Eventminigame.getInstance().getConfig().getString("LadderRace.afterGameStopTeleportWinner")).getSpawnLocation();
    }

    public static boolean isPlayerPlaying(Player player){
        return playerManager.containsKey(player) && playerManager.get(player).equals(PlayerState.PLAYING);
    }
    public static void saveWinnerData(){
        Eventminigame.getInstance().getConfig().set("Winner",winners);
        Eventminigame.getInstance().saveConfig();
        Eventminigame.getInstance().reloadConfig();
    }
}
