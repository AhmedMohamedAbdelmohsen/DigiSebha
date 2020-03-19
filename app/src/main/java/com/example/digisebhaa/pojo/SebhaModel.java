package com.example.digisebhaa.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sebha_table")
public class SebhaModel {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "text")
    private final String text;

    @ColumnInfo(name = "narated")
    private final String narated_by;

    @ColumnInfo(name = "description")
    private final String description;

    @ColumnInfo(name = "repeats")
    private final String repeats;

    @ColumnInfo(name = "type")
    private final int type;

    public SebhaModel(@NonNull String text, String narated_by, String description, String repeats, int type) {
        this.text = text;
        this.narated_by = narated_by;
        this.description = description;
        this.repeats = repeats;
        this.type = type;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public String getNarated_by() {
        return narated_by;
    }

    public String getDescription() {
        return description;
    }

    public String getRepeats() {
        return repeats;
    }

    public int getType() {
        return type;
    }
}
