package com.fic.notescategories.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.CategoryController;
import com.fic.notescategories.model.Category;
import com.fic.notescategories.model.CategoryWithCount;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Holder> {

    private List<CategoryWithCount> list;
    private final Context ctx;
    private final CategoryController controller;

    public CategoriesAdapter(List<CategoryWithCount> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
        this.controller = new CategoryController(ctx);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_category, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h, int position) {
        CategoryWithCount c = list.get(position);

        h.name.setText(c.category.categoryName);
        h.count.setText(c.notesCount + " notas");

        // Tocar tarjeta → ver notas de esa categoría
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, NotesActivity.class);
            i.putExtra("category_id", c.category.categoryId);
            i.putExtra("category_name", c.category.categoryName);
            ctx.startActivity(i);
        });

        // Botón menú → Editar / Eliminar
        h.menu.setOnClickListener(v -> showMenu(c));
    }

    private void showMenu(CategoryWithCount item) {
        String[] options = {"Editar", "Eliminar"};

        new AlertDialog.Builder(ctx)
                .setTitle(item.category.categoryName)
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        editCategory(item.category);
                    } else if (which == 1) {
                        deleteCategory(item);
                    }
                })
                .show();
    }

    private void editCategory(Category c) {
        Intent i = new Intent(ctx, AddEditCategoryActivity.class);
        i.putExtra("category_id", c.categoryId);
        i.putExtra("category_name", c.categoryName);
        ctx.startActivity(i);
    }

    private void deleteCategory(CategoryWithCount c) {
        if (c.notesCount > 0) {
            Toast.makeText(ctx, "No puedes eliminar una categoría con notas.", Toast.LENGTH_LONG).show();
            return;
        }

        new AlertDialog.Builder(ctx)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar la categoría \"" + c.category.categoryName + "\"?")
                .setPositiveButton("Sí", (d, w) -> {
                    controller.delete(c.category);
                    Toast.makeText(ctx, "Categoría eliminada", Toast.LENGTH_SHORT).show();

                    // Recargar lista desde BD
                    list = controller.getCategoriesWithCount();
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, count;
        ImageView menu;

        Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtCategoryName);
            count = itemView.findViewById(R.id.txtCategoryCount);
            menu = itemView.findViewById(R.id.btnMenuCategory);
        }
    }
}
