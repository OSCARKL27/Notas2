package com.fic.notescategories.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.NoteController;
import com.fic.notescategories.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Holder> {

    private List<Note> list;
    private final Context ctx;
    private final NoteController controller;
    private final Runnable onDataChanged;

    public NotesAdapter(List<Note> list, Context ctx, NoteController controller, Runnable onDataChanged) {
        this.list = list;
        this.ctx = ctx;
        this.controller = controller;
        this.onDataChanged = onDataChanged;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_note, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h, int position) {
        Note n = list.get(position);

        h.title.setText(n.noteTitle);
        h.content.setText(n.noteContent);

        // Click → editar
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, AddEditNoteActivity.class);
            i.putExtra("note_id", n.noteId);
            i.putExtra("note_title", n.noteTitle);
            i.putExtra("note_content", n.noteContent);
            i.putExtra("note_created_at", n.createdAt);
            i.putExtra("category_id", n.categoryId);
            ctx.startActivity(i);
        });

        // Long click → eliminar
        h.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(ctx)
                    .setTitle("Eliminar nota")
                    .setMessage("¿Deseas eliminar esta nota?")
                    .setPositiveButton("Sí", (d, w) -> {
                        controller.delete(n);
                        Toast.makeText(ctx, "Nota eliminada", Toast.LENGTH_SHORT).show();
                        onDataChanged.run();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title, content;
        Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtNoteTitle);
            content = itemView.findViewById(R.id.txtNoteContent);
        }
    }
}
