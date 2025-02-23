package org.gomadango0113.buildbattle.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.gomadango0113.buildbattle.manager.GameManager;
import org.gomadango0113.buildbattle.manager.RuleManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class BlockPlaceListener implements Listener {

    private static int now_place_size = 0;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Player build_player = GameManager.getBuildPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (build_player != null) {
            if (player.equals(build_player)) {
                if (RuleManager.isPlaceSizeRule()) {
                    int can_place_size = RuleManager.getCanPlaceSize();

                    now_place_size++;
                    if (can_place_size == now_place_size) {
                        ChatUtil.sendGlobalMessage(
                                "設置可能ブロック数を超えてしまいました! (" + can_place_size + "ブロック)　正解は" + GameManager.getBuild().getName() + "でした。\n" +
                                        "次のゲームまでお待ちください。");
                        now_place_size=0;
                        GameManager.nextGame();
                    }
                }
            }
        }
    }
}
