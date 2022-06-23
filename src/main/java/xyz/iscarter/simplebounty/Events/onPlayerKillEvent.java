package xyz.iscarter.simplebounty.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;

public class onPlayerKillEvent implements Listener {
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Entity killer = e.getPlayer().getKiller();

        if(killer instanceof Player) {
            Player p = ((Player) killer).getPlayer();
            KillsStorageUtils.addKill(p.getUniqueId().toString());
        }
    }
}
