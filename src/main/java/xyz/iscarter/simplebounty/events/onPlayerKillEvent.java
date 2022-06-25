package xyz.iscarter.simplebounty.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.iscarter.simplebounty.models.Kill;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;

public class onPlayerKillEvent implements Listener {
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player p = e.getPlayer();

        Entity killer = p.getKiller();


        if(killer instanceof Player) {
            Player killerPlayer = ((Player) killer).getPlayer();
            KillsStorageUtils.addKill(killer.getUniqueId().toString());
        }

        if(getPlayerKills(p) != null) {
            KillsStorageUtils.subtractKill(p.getUniqueId().toString());
        } else {
            KillsStorageUtils.subtractKill(p.getUniqueId().toString());
        }

    }

    private static Kill getPlayerKills(Player p) {
        Kill kills = KillsStorageUtils.getKills(p.getUniqueId().toString());

        if(kills != null) {
            return kills;
        }
        return null;
    }
}
