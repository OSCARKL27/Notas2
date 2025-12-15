package com.fic.notescategories.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.NoteController;
import com.fic.notescategories.model.Note;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText txtNoteTitle, txtNoteContent;
    Button btnSaveNote;
    NoteController controller;

    int categoryId;
    boolean isEditMode = false;
    int noteId = 0;
    String createdAtExisting = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        txtNoteTitle = findViewById(R.id.txtNoteTitle);
        txtNoteContent = findViewById(R.id.txtNoteContent);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        controller = new NoteController(this);

        // category_id siempre viene
        categoryId = getIntent().getIntExtra("category_id", 0);

        // ¿editar?
        if (getIntent().hasExtra("note_id")) {
            isEditMode = true;
            noteId = getIntent().getIntExtra("note_id", 0);
            String title = getIntent().getStringExtra("note_title");
            String content = getIntent().getStringExtra("note_content");
            createdAtExisting = getIntent().getStringExtra("note_created_at");

            if (title != null) txtNoteTitle.setText(title);
            if (content != null) txtNoteContent.setText(content);

            setTitle("Editar nota");
        } else {
            setTitle("Nueva nota");
        }

        btnSaveNote.setOnClickListener(v -> {
            String title = txtNoteTitle.getText().toString().trim();
            String content = txtNoteContent.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "note_title no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                Note n = new Note();
                n.noteId = noteId;
                n.noteTitle = title;
                n.noteContent = content;
                n.createdAt = (createdAtExisting != null)
                        ? createdAtExisting
                        : String.valueOf(System.currentTimeMillis());
                n.categoryId = categoryId;

                controller.update(n);
                Toast.makeText(this, "Nota actualizada", Toast.LENGTH_SHORT).show();
            } else {
                Note n = new Note();
                n.noteTitle = title;
                n.noteContent = content;
                n.createdAt = String.valueOf(System.currentTimeMillis());
                n.categoryId = categoryId;

                controller.add(n);
                Toast.makeText(this, "Nota agregada", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
