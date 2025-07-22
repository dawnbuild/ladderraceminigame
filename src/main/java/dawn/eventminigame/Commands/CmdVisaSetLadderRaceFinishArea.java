package dawn.eventminigame.Commands;

import dawn.eventminigame.Area;
import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.Listener.EditorModeListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CmdVisaSetLadderRaceFinishArea implements CommandExecutor {
    Map<Player, Area> editorMode = EditorModeListener.editorMode;


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("save")){
            //SAVE THE THING
            if (!editorMode.containsKey(player)) return false;
            String path = "LadderRace.FinishArea.";
            Area.save(path,editorMode.get(player).getLoc1(),editorMode.get(player).getLoc2());
        }else{
            Area GameArea = new Area(null,null);
            if (!editorMode.containsKey(player)) editorMode.put(player,GameArea);
            else{
                editorMode.remove(player);
                player.sendMessage(Eventminigame.ChatColor("&cKeluar dari mode editor"));
                return false;
            }
            player.sendMessage(Eventminigame.ChatColor("Memasuki mode editor. &eLeft Click untuk Pos 1 &rdan &eRight Click untuk Pos 2&r. &8[&b/VisaSetLadderRaceGameArea save&8] &runtuk menyimpan posisi yang sudah di set"));
            player.sendMessage(Eventminigame.ChatColor("Jalankan &bCommand &rsekali lagi untuk &cKeluar &rdari mode ini"));

        }
        return true;
    }
}
