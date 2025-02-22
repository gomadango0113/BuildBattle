package org.gomadango0113.buildbattle.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.gomadango0113.buildbattle.BuildBattle;
import org.gomadango0113.buildbattle.util.ChatUtil;

import java.util.*;

public class GameManager {

    //建築
    private static int build_time;
    private static Player now_build_player;
    private static Set<OfflinePlayer> build_players;

    //ゲーム
    private static GameStatus status;
    private static BukkitTask task;

    static {
        Collection<? extends Player> all_players = Bukkit.getOnlinePlayers();
        build_players = new HashSet<>(all_players);

        status = GameStatus.WAITING;
        build_time = 20;
    }

    public static void startGame() {
        if (status == GameStatus.WAITING) {
            final int[] count_time = {10};
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
                            ChatUtil.sendGlobalMessage("時間切れです。" + "\n" +
                                    "次のゲームまでお待ちください。");
                            nextGame();
                        }
                        else {
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

    public static void nextGame() {
        //ランダムにプレイヤーを選別
        List<OfflinePlayer> build_list = new ArrayList<>(build_players);
        Collections.shuffle(build_list);
        for (OfflinePlayer player : build_list) {
            if (player.isOnline() && player instanceof Player) {
                now_build_player = player.getPlayer();
                build_players.remove(player);
                break;
            }
        }

        build_time=20;
    }

    public enum GameStatus {
        WAITING, //ゲーム開始前
        COUNTING, //カウント中
        RUNNING, //ゲーム中
        ENDING, //ゲーム後
        UNKNOWN
    }

}
