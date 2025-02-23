package org.gomadango0113.buildbattle.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.gomadango0113.buildbattle.manager.GameManager;
import org.gomadango0113.buildbattle.manager.LocationManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Player build_player = GameManager.getBuildPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (build_player != null) {
            if (player.equals(build_player)) {
                if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) && player.isSneaking()) { //右クリック＆シフト
                    if (item != null) {
                        if (item.getType().isBlock()) { //アイテムがブロックであるか
                            Location arena_start = LocationManager.getBuildStartArena();
                            Location arena_end = LocationManager.getBuildEndArena();
                            int min_y = Math.min(arena_start.getBlockY(), arena_end.getBlockY());
                            arena_start.setY(min_y);
                            arena_end.setY(min_y);

                            LocationManager.fillBlock(item.getType(), arena_start, arena_end);
                            ChatUtil.sendMessage(build_player, "床のブロックを変更しました。");
                        }
                    }
                }
            }
        }
    }
}
