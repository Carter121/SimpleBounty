package xyz.iscarter.simplebounty.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.iscarter.simplebounty.models.Bounty;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;

public class onPlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        for (Bounty bounty : BountiesStorageUtils.getAllBounties()) {

            if (bounty.getPlayerName().equals(p.getName()) && bounty.getPlayerUUID() == null) {

                Bounty tempBounty = bounty;
                bounty.setPlayerUUID(p.getUniqueId().toString());
                BountiesStorageUtils.deleteBounty(p.getName());
                BountiesStorageUtils.createBounty(p.getUniqueId().toString(), tempBounty.getAmount(), tempBounty.getPlayerName());
                return;

            }

            if(bounty.getPlayerUUID() != null && bounty.getPlayerUUID().equals(p.getUniqueId().toString()) && !(bounty.getPlayerName().equals(p.getName()))) {

                Bounty tempBounty = bounty;
                BountiesStorageUtils.deleteBounty(bounty.getPlayerName());
                bounty.setPlayerName(p.getName());
                BountiesStorageUtils.createBounty(tempBounty.getPlayerUUID(), tempBounty.getAmount(), tempBounty.getPlayerName());
                break;
            }
        }
    }
}
