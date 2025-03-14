package org.gomadango0113.buildbattle.manager;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.gomadango0113.buildbattle.BuildBattle;
import org.gomadango0113.buildbattle.util.ChatUtil;

import java.io.IOException;
import java.util.*;

public class GameManager {

    //建築
    private static int build_time;
    private static final int default_build_time;
    private static Player now_build_player;
    private static Set<OfflinePlayer> build_players;
    private static BuildManager.Build now_build;
    private static List<BuildManager.Build> build_list;

    //ゲーム
    private static GameStatus status;
    private static BukkitTask task;
    private static World world;

    static {
        Collection<? extends Player> all_players = Bukkit.getOnlinePlayers();
        build_players = new HashSet<>(all_players);

        status = GameStatus.WAITING;
        build_time = 60;
        default_build_time = build_time;

        world = Bukkit.getWorlds().get(0);

        try {
            List<BuildManager.Build> get_build_list = BuildManager.getBuildList();
            if (get_build_list.isEmpty()) {
                build_list = BuildManager.getDefaultBuildList();
                Bukkit.getLogger().warning("[BuildBattle] JSONを取得できなかったため、デフォルトのビルドが使用されます。");
            }
            else {
                build_list = new ArrayList<>(get_build_list);
            }
        }
        catch (IOException e) {
            Bukkit.getLogger().warning("[BuildBattle] JSONを取得できなかったため、デフォルトのビルドが使用されます。");
            build_list = BuildManager.getDefaultBuildList();
        }
    }

    public static void startGame() {
        if (status == GameStatus.WAITING) {
            final int[] count_time = {3};
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (status == GameStatus.WAITING) {
                        if (count_time[0] == 0) {
                            ChatUtil.sendGlobalMessage("ゲームスタート!");

                            nextGame();
                            ScoreboardManager.setScoreboard(1);
                            status = GameStatus.RUNNING;
                        }
                        else {
                            ChatUtil.sendGlobalMessage("ゲーム開始まであと" + count_time[0] + "秒!");

                            count_time[0]--;
                        }
                    }
                    else if (status == GameStatus.RUNNING){
                        if (build_time == 0) {
                            if (build_players.isEmpty()) {
                                ChatUtil.sendGlobalMessage("ゲーム終了です。");
                                ChatUtil.sendGlobalMessage("===ランキング===" + "\n" +
                                        PointManager.getRankingString() + "\n" +
                                        "===========");
                                this.cancel();
                            }
                            else {
                                PointManager.addPoint(now_build_player.getName(), 1);
                                ChatUtil.sendGlobalMessage("時間切れです。" + "\n" +
                                        "次のゲームまでお待ちください。");
                                nextGame();
                            }
                        }
                        else {
                            if (now_build != null) {
                                //ヒント
                                if (build_time == default_build_time * 0.75) {
                                    ChatUtil.sendGlobalMessage("文字数ヒント：" + now_build.getName().length());
                                }
                                else if (build_time == default_build_time * 0.5) {
                                    ChatUtil.sendGlobalMessage("難しさヒント：" + now_build.getDifficulty());
                                }
                                else if (build_time == default_build_time * 0.25) {
                                    ChatUtil.sendGlobalMessage("ジャンルヒント：" + now_build.getDifficulty());
                                }
                            }

                            if (now_build_player != null) {
                                Location loc = now_build_player.getLocation();
                                if (!LocationManager.isBuildArena(loc)) {
                                    Location build_spawn = LocationManager.getBuildSpawn();
                                    now_build_player.teleport(build_spawn);
                                }
                            }

                            build_time--;
                        }
                    }
                }
            }.runTaskTimer(BuildBattle.getInstance(), 0L, 20L);
        }
    }

    /**
     * @return ゲームの状態
     */
    public static GameStatus getStatus() {
        return status;
    }

    /**
     * @return 残りの建築時間
     */
    public static int getBuildTime() {
        return build_time;
    }

    /**
     * @return 建築予定のプレイヤーのセット
     */
    public static Set<OfflinePlayer> getBuildPlayers() {
        return build_players;
    }

    /**
     * @return 現在建築しているプレイヤー
     */
    public static Player getBuildPlayer() {
        return now_build_player;
    }

    public static List<BuildManager.Build> getBuildList() {
        return build_list;
    }

    public static BuildManager.Build getBuild() {
        return now_build;
    }

    public static void nextGame() {
        if (!build_players.isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    //arena
                    LocationManager.fillBlock(Material.AIR, LocationManager.getBuildStartArena(), LocationManager.getBuildEndArena());

                    //ランダムに建築物を選別
                    Collections.shuffle(build_list);
                    now_build = build_list.get(0);
                    build_list.remove(now_build);

                    //ランダムにプレイヤーを選別
                    List<OfflinePlayer> build_player_list = new ArrayList<>(build_players);
                    Collections.shuffle(build_player_list);
                    for (OfflinePlayer player : build_player_list) {
                        if (player.isOnline() && player instanceof Player) {
                            now_build_player = player.getPlayer();
                            build_players.remove(player);

                            now_build_player.teleport(LocationManager.getBuildSpawn());
                            ChatUtil.sendMessage(now_build_player, "あなたが建築するものは" + now_build.getName()  +"です。");
                            break;
                        }
                    }

                    //ルール
                    RuleManager.randomRule();

                    build_time=20;
                }
            }.runTask(BuildBattle.getInstance());
        }
        else {
            ChatUtil.sendGlobalMessage("ゲーム終了です。");
            ChatUtil.sendGlobalMessage("===ランキング===" + "\n" +
                    PointManager.getRankingString() + "\n" +
                    "===========");
            task.cancel();
            task = null;
        }
    }

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        GameManager.world = world;
    }

    public enum GameStatus {
        WAITING, //ゲーム開始前
        COUNTING, //カウント中
        RUNNING, //ゲーム中
        ENDING, //ゲーム後
        UNKNOWN
    }

}
