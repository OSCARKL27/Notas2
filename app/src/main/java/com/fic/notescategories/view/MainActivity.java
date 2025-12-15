package com.fic.notescategories.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.CategoryController;
import com.fic.notescategories.model.CategoryWithCount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerCategories;
    FloatingActionButton fabAddCategory;
    Button btnHistory;

    CategoryController controller;
    CategoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new CategoryController(this);

        recyclerCategories = findViewById(R.id.recyclerCategories);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(this));

        fabAddCategory = findViewById(R.id.fabAddCategory);
        fabAddCategory.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditCategoryActivity.class))
        );

        // ✅ Botón Historial
        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<CategoryWithCount> list = controller.getCategoriesWithCount();
        adapter = new CategoriesAdapter(list, this);
        recyclerCategories.setAdapter(adapter);
    }
}
