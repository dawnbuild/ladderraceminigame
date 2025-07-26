package dawn.eventminigame.MiniGames.CustomItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFunc implements Listener {

    @EventHandler
    public void KnockbackStickUse(EntityDamageByEntityEvent e){
        Player player = (Player) e.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (item.hasItemMeta()){
            if (CustomItemManager.isKnockbackStick(meta)){
                int currentHitLeft = CustomItemManager.getKnockbackUsesLeft(item);
                CustomItemManager.setHitLeft(item,currentHitLeft-1);
                if (CustomItemManager.getKnockbackUsesLeft(item) <=0) item.setType(Material.AIR);
            }
        }
    }
}
