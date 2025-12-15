package com.fic.notescategories.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM categories ORDER BY category_name ASC")
    List<Category> getAllCategories();

    @Query("SELECT c.*, COUNT(n.note_id) AS notes_count " +
            "FROM categories c " +
            "LEFT JOIN notes n ON c.category_id = n.category_id " +
            "GROUP BY c.category_id " +
            "ORDER BY c.category_name ASC")
    List<CategoryWithCount> getCategoriesWithCount();
    @Query("SELECT COUNT(*) FROM categories WHERE category_name = :name")
    int categoryExists(String name);
}
