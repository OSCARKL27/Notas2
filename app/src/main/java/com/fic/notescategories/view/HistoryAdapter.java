package com.fic.notescategories.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fic.notescategories.R;
import com.fic.notescategories.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {

    private List<History> list;

    public HistoryAdapter(List<History> list) {
        this.list = list;
    }

    public void setData(List<History> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h, int position) {
        History item = list.get(position);
        h.action.setText(item.action);
        h.details.setText(item.details);
        h.date.setText(item.createdAt);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView action, details, date;
        Holder(View itemView) {
            super(itemView);
            action = itemView.findViewById(R.id.txtAction);
            details = itemView.findViewById(R.id.txtDetails);
            date = itemView.findViewById(R.id.txtDate);
        }
    }
}
