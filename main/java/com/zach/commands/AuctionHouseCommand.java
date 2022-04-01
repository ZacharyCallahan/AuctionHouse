package com.zach.commands;

import com.zach.AuctionHouse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AuctionHouseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                //add command list (help memu)
                return false;
            }
            switch (args[0]) {
                case "create":
                    AuctionHouse.getInstance().wandHandler.creation(player);
                    break;
            }

        }
        return false;
    }
}