package com.example.aol_blate_mobprog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aol_blate_mobprog.models.History;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    ArrayList<History> items;
    Context context;

    public HistoryAdapter(ArrayList<History> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDate.setText(item.getDate());
        holder.ivUser.setImageResource(item.getImageRes());

        // Logika warna status
        holder.tvStatus.setText(item.getStatus());
        if(item.getStatus().equals("Liked")) {
            holder.tvStatus.setTextColor(Color.parseColor("#E91E63")); // Pink
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#FBC02D")); // Kuning/Gold
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvStatus;
        ImageView ivUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivUser = itemView.findViewById(R.id.ivUser);
        }
    }
}