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
}
