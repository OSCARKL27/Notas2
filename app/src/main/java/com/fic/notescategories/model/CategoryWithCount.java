package com.fic.notescategories.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class CategoryWithCount {

    @Embedded
    public Category category;

    @ColumnInfo(name = "notes_count")
    public int notesCount;
}
