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
        holder.tvStatus.setText(item.getStatus());
        holder.tvDate.setText(item.getDate());

        // logic warna status
        if ("Like".equalsIgnoreCase(item.getStatus())) {
            holder.tvStatus.setTextColor(Color.parseColor("#F48FB1"));
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#FFF59D"));
        }

        // logic gambarnya
        String imageName = item.getImageStr();
        int defImage = R.drawable.ic_launcher_background; //kalo gambarnya null

        if (imageName != null && !imageName.isEmpty()) {
            int resId = holder.itemView.getContext().getResources().getIdentifier(
                    imageName.toLowerCase(), "drawable", holder.itemView.getContext().getPackageName());
            holder.ivAvatar.setImageResource(resId != 0 ? resId : defImage);
        } else {
            holder.ivAvatar.setImageResource(defImage);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvDate;
        ImageView ivAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivAvatar = itemView.findViewById(R.id.ivUser);
        }
    }
}