package org.gomadango0113.buildbattle.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.gomadango0113.buildbattle.BuildBattle;
import org.gomadango0113.buildbattle.util.ChatUtil;

import java.util.Random;

public class RuleManager {

    private static final BuildBattle instance = BuildBattle.getInstance();
    private static final FileConfiguration config = instance.getConfig();

    //設定
    private static int place_size = 0;
    private static int break_size = 0;

    public static void randomRule() {
        StringBuilder builder = new StringBuilder();

        if (isPlaceSizeRule()) {
            place_size = new Random().nextInt(10) + 1;
            builder.append("・設置ブロック数：" + place_size + "\n");
        }

        if (isBreakSizeRule()) {
            break_size = new Random().nextInt(10) + 1;
            builder.append("・破壊ブロック数：" + break_size + "\n");
        }

        if (!builder.toString().isEmpty()) {
            ChatUtil.sendMessage(GameManager.getBuildPlayer(), "ルール：" + builder);
        }
    }

    public static int getCanPlaceSize() {
        return place_size;
    }

    public static boolean isPlaceSizeRule() {
        return config.getBoolean("place_size_rule", true);
    }

    public static void setPlaceSizeRule(boolean flag) {
        config.set("place_size_rule", flag);
        instance.saveDefaultConfig();
    }

    public static int getCanBreakSize() {
        return break_size;
    }

    public static boolean isBreakSizeRule() {
        return config.getBoolean("break_size_rule", true);
    }

    public static void setBreakSizeRule(boolean flag) {
        config.set("break_size_rule", flag);
        instance.saveDefaultConfig();
    }
}
