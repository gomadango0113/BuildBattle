package org.gomadango0113.buildbattle.manager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.gomadango0113.buildbattle.BuildBattle;

public class LocationManager {

    private static final BuildBattle instance = BuildBattle.getInstance();
    private static final FileConfiguration config = instance.getConfig();
    private static final World world = GameManager.getWorld();

    public static Location getLobby() {
        if (world != null) {
            int x = config.getInt("location.lobby.x");
            int y = config.getInt("location.lobby.y");
            int z = config.getInt("location.lobby.z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static Location getBuildSpawn() {
        if (world != null) {
            int x = config.getInt("location.build_spawn.x");
            int y = config.getInt("location.build_spawn.y");
            int z = config.getInt("location.build_spawn.z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static Location getBuildStartArena() {
        if (world != null) {
            int x = config.getInt("location.build_arena.start.x");
            int y = config.getInt("location.build_arena.start.y");
            int z = config.getInt("location.build_arena.start.z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static Location getBuildEndArena() {
        if (world != null) {
            int x = config.getInt("location.build_arena.end.x");
            int y = config.getInt("location.build_arena.end.y");
            int z = config.getInt("location.build_arena.end.z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static boolean isBuildArena(Location loc) {
        if (getBuildStartArena() != null && getBuildEndArena() != null) {
            return isArena(loc, getBuildStartArena(), getBuildEndArena());
        }
        return false;
    }

    private static boolean isArena(Location check_loc, Location start, Location end) {
        int min_x = Math.min(start.getBlockX(), end.getBlockX());
        int max_x = Math.max(start.getBlockX(), end.getBlockX());

        int min_y = Math.min(start.getBlockY(), end.getBlockY());
        int max_y = Math.max(start.getBlockY(), end.getBlockY());

        int min_z = Math.min(start.getBlockZ(), end.getBlockZ());
        int max_z = Math.max(start.getBlockZ(), end.getBlockZ());

        int x = check_loc.getBlockX();
        int y = check_loc.getBlockY();
        int z = check_loc.getBlockZ();
        return x >= min_x && x <= max_x &&  //xが、最小X以上＆最大X以下
                y >= min_y && y <= max_y && //yが、最小Y以上＆最大Y以下
                z >= min_z && z <= max_z; //zが、最小Z以上＆最大Z以下
    }

}
