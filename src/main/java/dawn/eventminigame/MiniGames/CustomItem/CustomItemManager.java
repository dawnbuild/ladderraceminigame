package dawn.eventminigame.MiniGames.CustomItem;

import dawn.eventminigame.Eventminigame;
import dawn.eventminigame.Player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomItemManager {
    public static ItemStack KnockbackStick;

    public static void registerItem(){
        createKnockbackStick();
    }

    public static void createKnockbackStick(){
        int maxUses = Eventminigame.getInstance().getConfig().getInt("LadderRace.knockback-stick-max-use");
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Eventminigame.ChatColor("&eKnockback Stick"));
        meta.addEnchant(Enchantment.KNOCKBACK,1,true);
        List<String> lore = new ArrayList<>();
        addLore(lore,"&7Just a knockback");
        addLore(lore,"&7stick with limitation");
        addLore(lore,"&7Use it wisely!");
        addLore(lore,"&eHit Left:&b "+maxUses);
        meta.setLore(lore);
        item.setItemMeta(meta);
        KnockbackStick = item;
    }

    public static void giveKBStickToAll(){
        for (Player player: PlayerManager.playerManager.keySet()) player.getInventory().addItem(KnockbackStick);
    }

    public static boolean isKnockbackStick(ItemMeta meta){
        return meta.getDisplayName().equalsIgnoreCase(Eventminigame.ChatColor("&eKnockback Stick"));
    }

    public static int getKnockbackUsesLeft(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        return Integer.parseInt(Objects.requireNonNull(lore).get(3).split(" ")[2]);
    }

    public static void setHitLeft(ItemStack item,int hitleft){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        setLore(lore,3,"&eHit Left:&b "+hitleft);
        item.setItemMeta(meta);
    }

    public static void addLore(List<String> lore, String str){
        lore.add(Eventminigame.ChatColor(str));
    }
    public static void setLore(List<String> lore,int index, String str){
        lore.set(index,Eventminigame.ChatColor(str));
    }
}
