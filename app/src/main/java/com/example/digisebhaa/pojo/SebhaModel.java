package com.example.digisebhaa.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sebha_table")
public class SebhaModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private String narated_by;
    private String description;
    private Integer repeats;
    private int type;

    public SebhaModel(String text, String narated_by, String description, Integer repeats, int type) {
        this.text = text;
        this.narated_by = narated_by;
        this.description = description;
        this.repeats = repeats;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNarated_by() {
        return narated_by;
    }

    public void setNarated_by(String narated_by) {
        this.narated_by = narated_by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRepeats() {
        return repeats;
    }

    public void setRepeats(Integer repeats) {
        this.repeats = repeats;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
