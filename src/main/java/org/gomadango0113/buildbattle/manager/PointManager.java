package org.gomadango0113.buildbattle.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PointManager {

    private static Map<String, Integer> point_map = new HashMap<>();

    public static void addPoint(String name, Integer point) {
        point_map.put(name, point_map.getOrDefault(name, 0) + point);
        Bukkit.broadcastMessage(point_map.toString());
    }

    public static int getPoint(String name) {
        return point_map.get(name);
    }

    public static String getRankingString() {
        HashMap<String, Integer> rank_map = point_map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l1, l2) -> l2, LinkedHashMap::new)); //ポイント数MAPを、高い順にする（PlayerName, ネクサス数）

        ArrayList<Map.Entry<String, Integer>> entry_list = new ArrayList<>(rank_map.entrySet()); //ポイント数MAPを、リストにしたもの。（PlayerName, ポイント数）
        int rank = 0;
        int back_rank = Integer.MIN_VALUE; //前のランク
        int display_rank = 0;

        Map<Integer, Map<String, Integer>> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : entry_list) {
            if (entry.getValue() != back_rank) {
                display_rank = rank + 1; //前のランクと違かったら、上げる
            }
            rank++; //ランクを上げる
            back_rank = entry.getValue(); //前の順位

            map.putIfAbsent(display_rank, new HashMap<>());
            map.get(display_rank).put(entry.getKey(), entry.getValue());
        }

        StringBuilder builder = new StringBuilder();
        for (int n = 1; n <= map.size(); n++) {
            Map<String, Integer> map_rank = map.get(n);
            for (Map.Entry<String, Integer> entry : map_rank.entrySet()) {
                String display_name = n + "位（" + entry.getKey() + "）"; //名前の表示例：1位（Blockgrass）

                builder.append(display_name + ":" + entry.getValue() + "\n");
            }
        }

        return builder.toString();
    }
}
