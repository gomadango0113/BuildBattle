package org.gomadango0113.buildbattle.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.gomadango0113.buildbattle.manager.GameManager;
import org.gomadango0113.buildbattle.manager.LocationManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Player build_player = GameManager.getBuildPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (build_player != null) {
            if (player.equals(build_player)) {
                if (!LocationManager.isBuildArena(loc)) {
                    ChatUtil.sendMessage(player, "範囲外のブロックは破壊できません。");
                    event.setCancelled(true);
                }
            }
        }
    }

}
