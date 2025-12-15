package com.fic.notescategories.controller;

import android.content.Context;

import com.fic.notescategories.model.AppDatabase;
import com.fic.notescategories.model.History;

import java.util.List;

public class HistoryController {

    private final AppDatabase db;

    public HistoryController(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public void log(String action, String details) {
        if (action == null || action.trim().isEmpty()) return;

        History h = new History();
        h.action = action.trim();
        h.createdAt = String.valueOf(System.currentTimeMillis());
        h.details = (details == null) ? "" : details.trim();

        db.historyDao().insertHistory(h);
    }

    public List<History> getAll() {
        return db.historyDao().getAllHistory();
    }

    public List<History> filter(String action, String fromTs, String toTs) {
        boolean hasAction = action != null && !action.equals("all");
        boolean hasDates = fromTs != null && toTs != null && !fromTs.isEmpty() && !toTs.isEmpty();

        if (hasAction && hasDates) return db.historyDao().getHistoryByActionAndDateRange(action, fromTs, toTs);
        if (hasAction) return db.historyDao().getHistoryByAction(action);
        if (hasDates) return db.historyDao().getHistoryByDateRange(fromTs, toTs);
        return db.historyDao().getAllHistory();
    }
}
