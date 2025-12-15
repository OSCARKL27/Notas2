package com.fic.notescategories.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id")
    public int historyId;

    @ColumnInfo(name = "action")
    public String action; // "insert_note", "update_note", "delete_note", etc.

    @ColumnInfo(name = "created_at")
    public String createdAt; // timestamp String

    @ColumnInfo(name = "details")
    public String details; // ej: título de la nota
}
