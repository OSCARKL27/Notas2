package com.fic.notescategories.controller;

import android.content.Context;

import com.fic.notescategories.model.AppDatabase;
import com.fic.notescategories.model.Category;
import com.fic.notescategories.model.CategoryWithCount;

import java.util.List;

public class CategoryController {

    private final AppDatabase db;

    public CategoryController(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<CategoryWithCount> getCategoriesWithCount() {
        return db.categoryDao().getCategoriesWithCount();
    }

    public void add(Category c) {
        db.categoryDao().insertCategory(c);
    }

    public void update(Category c) {
        db.categoryDao().updateCategory(c);
    }

    public void delete(Category c) {
        db.categoryDao().deleteCategory(c);
    }

    public boolean exists(String name) {
        return db.categoryDao().categoryExists(name) > 0;
    }
}
