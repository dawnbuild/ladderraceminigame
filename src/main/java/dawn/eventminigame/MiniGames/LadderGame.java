package dawn.eventminigame.MiniGames;

import dawn.eventminigame.Area;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.Player.PlayerManager;
import dawn.eventminigame.Player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class LadderGame {
    Area GameArea;
    Area FinishArea;
    Location spawn;
    World world;
    public LadderGame(Area GameArea, Area FinishArea, Location spawn, World world){
        this.GameArea = GameArea;
        this.FinishArea = FinishArea;
        this.spawn = spawn;
        this.world = world;
    }
    GameState gameState = null;
    BukkitTask gameTask = null;
    public void setGameState(GameState gameState){
        this.gameState = gameState;
        switch (gameState){
            case START:
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (player.isOp() || player.hasPermission("visantaraminigame.admin"))continue;
                    PlayerManager.setPlayerState(player, PlayerState.PLAYING);
                    player.teleport(spawn);
                }
                gameTask = new BukkitRunnable() {
                    int lamapermainan = Eventminigame.getInstance().getConfig().getInt("LadderRace.lamapermainan") * 20;
                    int countdownmulai = Eventminigame.getInstance().getConfig().getInt("LadderRace.countdownmulai")*20;
                    @Override
                    public void run() {
                        if (countdownmulai>0) countdownmulai-=20;
                        if (countdownmulai<=10*20 && countdownmulai>0){
                            //SENDPLAYERMESSAGE GAME AKAN DIMULAI DALAM 10 9 8 7 6 5 4 3 2 1
                            for (Player player:PlayerManager.playerManager.keySet()){
                                player.sendTitle(Eventminigame.ChatColorNonPrefix("&aGame Start Soon"),Eventminigame.ChatColorNonPrefix("&eGame Starting in &a" + countdownmulai));
                            }
                        } else if (countdownmulai<=0) {
                            lamapermainan-=1*20;
                            for (Player player : PlayerManager.playerManager.keySet()){
                                if (!FinishArea.isPlayerInsideArea(player))continue;
                                player.sendMessage(Eventminigame.ChatColor("&eAnda Memasuki Daerah Finish!!!"));
                            }


                            if (lamapermainan<=0) {
                                setGameState(GameState.STOP);
                            }
                        }
                    }
                }.runTaskTimer(Eventminigame.getInstance(),0,1);
            case STOP:
                Bukkit.broadcastMessage(Eventminigame.ChatColor("&cWaktu habis, terimakasih telah bermain!!"));
                gameTask.cancel();
        }
    }

    //Save The spawn
    public static void setSpawn(String path,Location location){
        Eventminigame.getInstance().getConfig().set(path+".x",location.getX());
        Eventminigame.getInstance().getConfig().set(path+".y",location.getY());
        Eventminigame.getInstance().getConfig().set(path+".z",location.getZ());
        Eventminigame.getInstance().saveConfig();
        Eventminigame.getInstance().reloadConfig();
    }
    public static Location getSpawnLocationFromConfig(){
        int x = Eventminigame.getInstance().getConfig().getInt("LadderRace.Spawn.x");
        int y = Eventminigame.getInstance().getConfig().getInt("LadderRace.Spawn.y");
        int z = Eventminigame.getInstance().getConfig().getInt("LadderRace.Spawn.z");
        World world = Bukkit.getWorld(UUID.fromString(Eventminigame.getInstance().getConfig().getString("LadderRace.world.uuid")));
        return new Location(world,x,y,z);
    }

    //GETTER
    public Area getGameArea(){return GameArea;}
    public Area getFinishArea(){return FinishArea;}
    public Location getSpawnLocation(){return spawn;}
    public World getWorld(){return world;}
    public GameState getGameState(){return gameState;}
}
