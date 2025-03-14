package org.gomadango0113.buildbattle;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.gomadango0113.buildbattle.command.GameStartCommand;
import org.gomadango0113.buildbattle.command.PointCommand;
import org.gomadango0113.buildbattle.listener.BlockBreakListener;
import org.gomadango0113.buildbattle.listener.BlockPlaceListener;
import org.gomadango0113.buildbattle.listener.PlayerChatListener;
import org.gomadango0113.buildbattle.listener.PlayerInteractListener;
import org.gomadango0113.buildbattle.manager.ScoreboardManager;

public final class BuildBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand();
        registerListener();
        ScoreboardManager.setScoreboard(0);
        saveDefaultConfig();

        getLogger().info("[BuildBattle] プラグインの準備ができました。");
    }

    @Override
    public void onDisable() {

    }

    private void registerCommand() {
        getCommand("buildbattle_start").setExecutor(new GameStartCommand());
        getCommand("buildbattle_point").setExecutor(new PointCommand());
    }

    private void registerListener() {
        PluginManager plm = getServer().getPluginManager();

        plm.registerEvents(new PlayerChatListener(), this);
        plm.registerEvents(new BlockBreakListener(), this);
        plm.registerEvents(new BlockPlaceListener(), this);
        plm.registerEvents(new PlayerInteractListener(), this);
    }

    public static BuildBattle getInstance() {
        return BuildBattle.getPlugin(BuildBattle.class);
    }
}

