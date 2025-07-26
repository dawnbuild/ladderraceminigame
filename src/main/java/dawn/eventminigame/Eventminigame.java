package dawn.eventminigame;

import dawn.eventminigame.Commands.*;
import dawn.eventminigame.Listener.EditorModeListener;
import dawn.eventminigame.Listener.PlayerEvent;
import dawn.eventminigame.MiniGames.CustomItem.CustomItemManager;
import dawn.eventminigame.MiniGames.CustomItem.ItemFunc;
import dawn.eventminigame.Player.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Eventminigame extends JavaPlugin {
    static Eventminigame plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin=this;
        this.saveDefaultConfig();
        this.getCommand("VisaGiveKBStick").setExecutor(new CmdVisaGiveKBStick());
        this.getCommand("VisaGiveKBStick").setTabCompleter(new CmdVisaGiveKBStick());

        CustomItemManager.registerItem();

        this.getCommand("VisaSetLadderRaceGameArea").setExecutor(new CmdVisaSetLadderRaceGameArea());
        this.getCommand("VisaStartGame").setExecutor(new CmdVisaStartGame());
        this.getCommand("VisaStopLadderRaceGame").setExecutor(new CmdVisaStopLadderRaceGame());
        this.getCommand("VisaSetLadderRaceFinishArea").setExecutor(new CmdVisaSetLadderRaceFinishArea());
        this.getCommand("VisaSetLadderRaceGameSpawn").setExecutor(new CmdVisaSetLadderRaceSpawn());
        this.getCommand("VisaSetGate").setExecutor(new CmdVisaSetGate());
        this.getCommand("VisaReload").setExecutor(new CmdVisaReloadConfig());
        this.getServer().getPluginManager().registerEvents(new EditorModeListener(),this);
        this.getServer().getPluginManager().registerEvents(new ItemFunc(),this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvent(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Eventminigame getInstance(){
        return plugin;
    }

    public static String ChatColor(String strings){
        return ChatColor.translateAlternateColorCodes('&',strings);
    }
    public static void sendTittleToAllPlayer(String Title,String subTitle){
        for(Player player: PlayerManager.playerManager.keySet()){
            player.sendTitle(Eventminigame.ChatColor(Title),Eventminigame.ChatColor(subTitle));
        }
    }

    public static void playSoundToAllPlayer(Sound sound){
        for(Player player: PlayerManager.playerManager.keySet()){
            player.playSound(player,sound,1.0f,1.0f);
        }
    }

    public static void changePlayerGamemode(Player player,GameMode gameMode){
        player.setGameMode(gameMode);
    }
}
