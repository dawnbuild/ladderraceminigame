package dawn.eventminigame.MiniGames.LadderRace;

import dawn.eventminigame.Area;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.Player.PlayerManager;
import dawn.eventminigame.Player.PlayerState;
import dawn.eventminigame.Player.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LadderGame {
    Area GameArea;
    Area FinishArea;
    Area Gate;
    Location spawn;
    World world;
    public LadderGame(Area GameArea, Area FinishArea,Area Gate, Location spawn, World world){
        this.GameArea = GameArea;
        this.FinishArea = FinishArea;
        this.spawn = spawn;
        this.world = world;
        this.Gate = Gate;
    }


    int gametime = Eventminigame.getInstance().getConfig().getInt("LadderRace.lamapermainan");
    int countdown = Eventminigame.getInstance().getConfig().getInt("LadderRace.countdownmulai");
    GameState gameState = null;
    public static int totalWinner = 0;

    BukkitTask task;
    public static BukkitTask stTask;
    public void setGameState(GameState gameState){
        this.gameState = gameState;
        stTask = task;
        Area gerbang = Area.getInstanceFromConfig("LadderRace.gate");

//        String BlockMaterial = Objects.requireNonNull(Eventminigame.getInstance().getConfig().getString("LadderRace.gate.block")).toUpperCase();

        switch (gameState){
            case START:

                for (Player player : Bukkit.getOnlinePlayers()){
                    if (player.isOp())continue;
                    PlayerManager.setPlayerState(player, PlayerState.PLAYING);
                    player.teleport(spawn);
                }
                ScoreboardManager.addPlayerScoreboard();
                List<Block> blocks = gerbang.getBlocks();
                Eventminigame.sendTittleToAllPlayer(Eventminigame.ChatColor("&aGame Will Start in &b" + countdown),"Get Ready!!");
                Bukkit.broadcastMessage(Eventminigame.ChatColor("&aGame Will Start in &b" + countdown));
                 task = new BukkitRunnable() {
                    int maxWinner = Eventminigame.getInstance().getConfig().getInt("LadderRace.maxplayerwin");
                    int timer = 0;
                    boolean opendoor = true;
                    @Override
                    public void run() {
                        if (countdown<0){
                            ScoreboardManager.update(timer);
                            //MULAI GAME
                            if (opendoor){
                                opendoor=false;
                                System.out.println(blocks.size());
                                for (Block block : blocks){
                                    block.setType(Material.AIR);
                                }
                                Eventminigame.playSoundToAllPlayer(Sound.ENTITY_ENDER_DRAGON_GROWL);
                                Eventminigame.sendTittleToAllPlayer("&aGood Luck!!","&aHave Fun!!");
                            }
                            //ELIMINASI JIKA SUDAH KALAH
                            if (totalWinner==maxWinner)setGameState(GameState.STOP);
                            else if (gametime < 0)setGameState(GameState.STOP);
                            timer++;
                            gametime--;
                        }if (countdown <= 10 && countdown>=0){
                            Eventminigame.playSoundToAllPlayer(Sound.BLOCK_NOTE_BLOCK_BELL);
                            Eventminigame.sendTittleToAllPlayer("&eGet Ready!!","&aGame Starting in &b" + countdown);
                            Bukkit.broadcastMessage(Eventminigame.ChatColor("&aGame Will Start in &b" + countdown));
                        }
                        countdown--;
                    }
                }.runTaskTimer(Eventminigame.getInstance(), 0L, 20L);
                break;
            case STOP:
                for (Player loser : PlayerManager.playerManager.keySet()){
                    if (PlayerManager.playerManager.get(loser)==PlayerState.PLAYING){
                        PlayerManager.setPlayerState(loser,PlayerState.LOSER);
                    }
                }
                for (Block block : gerbang.getBlocks()){
                    if (block.getType().equals(Material.AIR))block.setType(Material.WHITE_WOOL);
                }
                PlayerManager.saveWinnerData();
                Bukkit.broadcastMessage(Eventminigame.ChatColor("&cWaktu habis, terimakasih telah bermain!!"));
                task.cancel();
                ScoreboardManager.sb.clear();
                break;

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

    public GameState getGameState(){
        return gameState;
    }
    public Area getFinishArea() {
        return FinishArea;
    }

}
