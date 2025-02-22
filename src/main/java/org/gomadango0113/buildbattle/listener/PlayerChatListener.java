package org.gomadango0113.buildbattle.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.gomadango0113.buildbattle.manager.BuildManager;
import org.gomadango0113.buildbattle.manager.GameManager;
import org.gomadango0113.buildbattle.util.ChatUtil;

public class PlayerChatListener implements Listener {
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player chat_player = event.getPlayer();
        Player build_player = GameManager.getBuildPlayer();
        String message = event.getMessage();
        BuildManager.Build now_build = GameManager.getBuild();
        
        if (now_build != null) {
            String build_name = now_build.getName();
            if (message.equalsIgnoreCase(build_name)) {
                ChatUtil.sendGlobalMessage(chat_player.getName() + "さん正解です! " + "正解は" + build_name + "でした! \n" +
                        "次のゲームまでお待ちください。");
                GameManager.nextGame();
            }
        }
    }
    
}
