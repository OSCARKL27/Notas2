package com.fic.notescategories.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.CategoryController;
import com.fic.notescategories.model.Category;

public class AddEditCategoryActivity extends AppCompatActivity {

    EditText txtCategoryName;
    Button btnSaveCategory;
    CategoryController controller;

    boolean isEditMode = false;
    int categoryId = 0;
    String originalName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        controller = new CategoryController(this);

        // ¿Venimos a editar?
        if (getIntent() != null && getIntent().hasExtra("category_id")) {
            isEditMode = true;
            categoryId = getIntent().getIntExtra("category_id", 0);
            originalName = getIntent().getStringExtra("category_name");

            if (originalName != null) {
                txtCategoryName.setText(originalName);
            }
            setTitle("Editar categoría");
        } else {
            setTitle("Nueva categoría");
        }

        btnSaveCategory.setOnClickListener(v -> {
            String name = txtCategoryName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Si cambia el nombre, validamos duplicado
            if (!name.equalsIgnoreCase(originalName) && controller.exists(name)) {
                Toast.makeText(this, "Ya existe una categoría con ese nombre.", Toast.LENGTH_LONG).show();
                return;
            }

            if (isEditMode) {
                Category c = new Category();
                c.categoryId = categoryId;
                c.categoryName = name;
                controller.update(c);
                Toast.makeText(this, "Categoría actualizada", Toast.LENGTH_SHORT).show();
            } else {
                Category c = new Category();
                c.categoryName = name;
                controller.add(c);
                Toast.makeText(this, "Categoría agregada", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
