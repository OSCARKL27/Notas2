package com.fic.notescategories.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.NoteController;
import com.fic.notescategories.model.Note;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    RecyclerView recyclerNotes;
    FloatingActionButton fabAddNote;
    EditText txtSearch;

    NoteController controller;
    int categoryId;
    String categoryName;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        categoryId = getIntent().getIntExtra("category_id", 0);
        categoryName = getIntent().getStringExtra("category_name");


        MaterialToolbar toolbar = findViewById(R.id.toolbarNotes);
        toolbar.setTitle("Notas: " + categoryName);
        setSupportActionBar(toolbar);

        controller = new NoteController(this);

        recyclerNotes = findViewById(R.id.recyclerNotes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));

        fabAddNote = findViewById(R.id.fabAddNote);
        txtSearch = findViewById(R.id.txtSearchNote);

        fabAddNote.setOnClickListener(v -> {
            Intent i = new Intent(this, AddEditNoteActivity.class);
            i.putExtra("category_id", categoryId);
            startActivity(i);
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadNotes(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(txtSearch.getText().toString());
    }

    private void loadNotes(String query) {
        List<Note> notes;
        if (query == null || query.trim().isEmpty()) {
            notes = controller.getByCategory(categoryId);
        } else {
            notes = controller.searchInCategory(categoryId, query.trim());
        }
        adapter = new NotesAdapter(notes, this, controller, this::reloadList);
        recyclerNotes.setAdapter(adapter);
    }

    private void reloadList() {
        loadNotes(txtSearch.getText().toString());
    }
}
