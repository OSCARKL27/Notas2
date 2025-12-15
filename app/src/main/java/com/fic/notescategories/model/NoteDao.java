package com.fic.notescategories.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes WHERE category_id = :categoryId ORDER BY created_at DESC")
    List<Note> getNotesByCategory(int categoryId);

    @Query("SELECT * FROM notes " +
            "WHERE category_id = :categoryId AND " +
            "(note_title LIKE '%' || :query || '%' " +
            "OR note_content LIKE '%' || :query || '%') " +
            "ORDER BY created_at DESC")
    List<Note> searchNotesInCategory(int categoryId, String query);
}
