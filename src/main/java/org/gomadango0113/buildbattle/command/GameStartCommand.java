package org.gomadango0113.buildbattle.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.gomadango0113.buildbattle.manager.GameManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class GameStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("buildbattle_start")) {
            ChatUtil.sendMessage(send, "ゲームを開始しています...");
            GameManager.startGame();
        }
        return false;
    }

}
