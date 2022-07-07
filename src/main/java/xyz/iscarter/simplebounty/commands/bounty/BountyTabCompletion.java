package xyz.iscarter.simplebounty.commands.bounty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BountyTabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1) {

            List<String> possibleCommands = new ArrayList<>();
            possibleCommands.add("list");

            return possibleCommands;

        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("list")) {

                List<String> possibleCommands = new ArrayList<>();
                possibleCommands.add("self");

                return possibleCommands;
            }
        }



        return null;
    }

}
