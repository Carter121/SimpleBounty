package xyz.iscarter.simplebounty.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Kill;
import xyz.iscarter.simplebounty.models.KillStreak;
import xyz.iscarter.simplebounty.utils.KillStreakStorageUtils;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;

import java.util.ArrayList;
import java.util.UUID;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {

    private final SimpleBounty plugin;

    public PlaceholderExpansion(SimpleBounty plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simplebounty";
    }

    @Override
    public @NotNull String getAuthor() {
        return "IsCarter";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("killstreak")) {

            KillStreak streak = KillStreakStorageUtils.getStreak(player.getUniqueId());

            String streakNum = "0";

            if(streak != null) {
                streakNum = String.valueOf(streak.getStreak());
            }

            return streakNum;
        }

        if(params.equalsIgnoreCase("maxkillstreak")) {

            KillStreak streak = KillStreakStorageUtils.getStreak(player.getUniqueId());

            String streakNum = "0";

            if(streak != null) {
                streakNum = String.valueOf(streak.getMaxStreak());
            }

            return streakNum;

        }

        if(params.equalsIgnoreCase("killpointstop")) {

            StringBuilder list = new StringBuilder();

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            for(int i = 0; i < kills.size(); i++) {

                Kill currentKill = kills.get(i);

                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

                if(i + 1 == kills.size()) {
                    list.append(i+1 + ". " + p.getName() + " " + currentKill.getKills());
                    continue;
                }
                list.append(i+1 + ". " + p.getName() + " " + currentKill.getKills() + "\n");
            }

            return list.toString();


        }

        if(params.equalsIgnoreCase("killpointstop1")) {
            int place = 1;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop2")) {
            int place = 2;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop3")) {
            int place = 3;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop4")) {
            int place = 4;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop5")) {
            int place = 5;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop6")) {
            int place = 6;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop7")) {
            int place = 7;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop8")) {
            int place = 8;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop9")) {
            int place = 9;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        if(params.equalsIgnoreCase("killpointstop10")) {
            int place = 10;

            ArrayList<Kill> kills = KillsStorageUtils.sort();

            kills = reverseArrayList(kills);

            if(kills.size() < place) {
                return nonePlace(place);
            }

            Kill currentKill = kills.get(place - 1);

            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(currentKill.getPlayerUUID()));

            return placeTxt(place,p.getName(), currentKill.getKills());

        }

        return null;
    }

    private static ArrayList<Kill> reverseArrayList(ArrayList<Kill> alist)
    {
        ArrayList<Kill> revArrayList = new ArrayList<Kill>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            revArrayList.add(alist.get(i));
        }

        return revArrayList;
    }

    private static String nonePlace(int place) {
        return (ChatColor.YELLOW + "" + place + ". " + ChatColor.GREEN + "None: " + ChatColor.RED + ChatColor.BOLD + 0);
    }

    private static String placeTxt(int place, String name, int kills) {
        return (ChatColor.YELLOW + "" + place + ". " + ChatColor.GREEN + name + ": " + ChatColor.RED + ChatColor.BOLD + kills);
    }
}
