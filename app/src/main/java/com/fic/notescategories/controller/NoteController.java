package com.fic.notescategories.controller;

import android.content.Context;

import com.fic.notescategories.model.AppDatabase;
import com.fic.notescategories.model.Note;

import java.util.List;

public class NoteController {

    private final AppDatabase db;
    private final HistoryController history;

    public NoteController(Context context) {
        db = AppDatabase.getInstance(context);
        history = new HistoryController(context);
    }

    public List<Note> getByCategory(int categoryId) {
        return db.noteDao().getNotesByCategory(categoryId);
    }

    public List<Note> searchInCategory(int categoryId, String query) {
        return db.noteDao().searchNotesInCategory(categoryId, query);
    }

    public void add(Note n) {
        db.noteDao().insertNote(n);
        history.log("insert_note", safeTitle(n));
    }

    public void update(Note n) {
        db.noteDao().updateNote(n);
        history.log("update_note", safeTitle(n));
    }

    public void delete(Note n) {
        db.noteDao().deleteNote(n);
        history.log("delete_note", safeTitle(n));
    }

    private String safeTitle(Note n) {
        if (n == null) return "";
        return (n.noteTitle == null) ? "" : n.noteTitle.trim();
    }
}
