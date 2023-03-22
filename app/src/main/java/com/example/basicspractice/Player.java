package com.example.basicspractice;

public class Player {

    // COLUMNS
    private int id;
    private String player_name;
    private int current_stage;
    private boolean is_game_complete;
    private String stage1_rt; // Remaining time
    private String stage1_best_rt; // Shortest/best remaining time
    private String stage2_rt;
    private String stage2_best_rt;

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer_name() { return player_name; }
    public void setPlayer_name(String player_name)
    {
        this.player_name = player_name;
    }

    public int getCurrent_stage() {
        return current_stage;
    }
    public void setCurrent_stage(int current_stage) {
        this.current_stage = current_stage;
    }

    public String getStage1_rt() {
        return stage1_rt;
    }
    public void setStage1_rt(String stage1_rt) {
        this.stage1_rt = stage1_rt;
    }

    public String getStage1_best_rt() {
        return stage1_best_rt;
    }
    public void setStage1_best_rt(String stage1_best_rt) {
        this.stage1_best_rt = stage1_best_rt;
    }

    public String getStage2_rt() {
        return stage2_rt;
    }
    public void setStage2_rt(String stage2_rt) {
        this.stage2_rt = stage2_rt;
    }

    public String getStage2_best_rt() {
        return stage2_best_rt;
    }
    public void setStage2_best_rt(String stage2_best_rt) {
        this.stage2_best_rt = stage2_best_rt;
    }

    public boolean isIs_game_complete() {
        return is_game_complete;
    }

    public void setIs_game_complete(boolean is_game_complete) {
        this.is_game_complete = is_game_complete;
    }

    // CONSTRUCTOR
    public Player(String player_name, int current_stage, boolean is_game_complete, String stage1_rt, String stage1_best_rt, String stage2_rt, String stage2_best_rt) {
        this.player_name = player_name;
        this.current_stage = current_stage;
        this.is_game_complete = is_game_complete;
        this.stage1_rt = stage1_rt;
        this.stage1_best_rt = stage1_best_rt;
        this.stage2_rt = stage2_rt;
        this.stage2_best_rt = stage2_best_rt;
    }
}
