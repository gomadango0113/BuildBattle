package org.gomadango0113.buildbattle;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuildBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand();
        registerListener();

        getLogger().info("[BuildBattle] プラグインの準備ができました。");
    }

    @Override
    public void onDisable() {

    }

    private void registerCommand() {

    }

    private void registerListener() {
        PluginManager plm = getServer().getPluginManager();
    }

    public static BuildBattle getInstance() {
        return BuildBattle.getPlugin(BuildBattle.class);
    }
}

