package org.gomadango0113.buildbattle.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.gomadango0113.buildbattle.manager.PointManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class PointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("buildbattle_point")) {
            if (args[0].equalsIgnoreCase("ranking")) {
                ChatUtil.sendGlobalMessage("===ランキング===" + "\n" +
                        PointManager.getRankingString() + "\n" +
                        "===========");
            }
        }
        return false;
    }

}
