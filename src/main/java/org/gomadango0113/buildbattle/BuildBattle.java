package org.gomadango0113.buildbattle;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.gomadango0113.buildbattle.command.GameStartCommand;
import org.gomadango0113.buildbattle.manager.ScoreboardManager;

public final class BuildBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand();
        registerListener();
        ScoreboardManager.setScoreboard(0);

        getLogger().info("[BuildBattle] プラグインの準備ができました。");
    }

    @Override
    public void onDisable() {

    }

    private void registerCommand() {
        getCommand("buildbattle_start").setExecutor(new GameStartCommand());
    }

    private void registerListener() {
        PluginManager plm = getServer().getPluginManager();
    }

    public static BuildBattle getInstance() {
        return BuildBattle.getPlugin(BuildBattle.class);
    }
}

