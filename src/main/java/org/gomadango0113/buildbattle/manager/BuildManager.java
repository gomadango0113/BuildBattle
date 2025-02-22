package org.gomadango0113.buildbattle.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.gomadango0113.buildbattle.BuildBattle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class BuildManager {

    private static final Gson gson;

    static  {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static List<Build> getDefaultBuildList() {
        return Arrays.asList(
                new Build("コンビニ", "建物", 1),
                new Build("動物園", "建物", 1),
                new Build("スーパー", "建物", 1),
                new Build("実家", "建物", 1),
                new Build("マイクラ", "ゲーム", 1));
    }

    public static List<Build> getBuildList() throws IOException {
        JsonObject json = getRawJson();

        List<Build> build_list = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            String build_name = entry.getKey();
            Build build = getBuild(build_name);
            build_list.add(build);
        }

        return build_list;
    }

    public static Build getBuild(String name) throws IOException {
        JsonObject json = getRawJson();
        if (json.has(name)) {
            JsonObject build_obj = json.getAsJsonObject(name);
            String build_genre = build_obj.get("genre").getAsString();
            int build_difficulty = build_obj.get("difficulty").getAsInt();

            return new Build(name, build_genre, build_difficulty);
        }
        return null;
    }

    public static JsonObject getRawJson() throws IOException {
        createJson();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    public static void writeFile(String date) {
        try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            write.write(date);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createJson() throws IOException {
        if (!getFile().exists()) {
            BuildBattle.getInstance().getDataFolder().mkdir();
            getFile().createNewFile();
            writeFile("{}");
        }
    }

    private static File getFile() {
        return new File(BuildBattle.getInstance().getDataFolder(), "build_list.json");
    }

    public static class Build {
        private final String name;
        private final String genre;
        private final int difficulty;

        public Build(String name, String genre, int difficulty) {
            this.name = name;
            this.genre = genre;
            this.difficulty = difficulty;
        }

        public String getName() {
            return name;
        }

        public String getGenre() {
            return genre;
        }

        public int getDifficulty() {
            return difficulty;
        }
    }
}
