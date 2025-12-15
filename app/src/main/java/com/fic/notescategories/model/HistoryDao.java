package com.fic.notescategories.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    long insertHistory(History h);

    @Query("SELECT * FROM history ORDER BY history_id DESC")
    List<History> getAllHistory();

    @Query("SELECT * FROM history WHERE action = :action ORDER BY history_id DESC")
    List<History> getHistoryByAction(String action);

    // Filtrar por rango de fechas (timestamps en String numérico)
    @Query("SELECT * FROM history " +
            "WHERE created_at BETWEEN :fromTs AND :toTs " +
            "ORDER BY history_id DESC")
    List<History> getHistoryByDateRange(String fromTs, String toTs);

    @Query("SELECT * FROM history " +
            "WHERE action = :action AND created_at BETWEEN :fromTs AND :toTs " +
            "ORDER BY history_id DESC")
    List<History> getHistoryByActionAndDateRange(String action, String fromTs, String toTs);
}
