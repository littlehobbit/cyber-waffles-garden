package com.example.taganrogdefender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PassportAdapter extends RecyclerView.Adapter<PassportAdapter.ItemViewHolder> {
    private ArrayList<PassportItem> _list;
    private OnItemClickListener _clickListener;

    public PassportAdapter(ArrayList<PassportItem> list)
    {
        _list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        _clickListener = listener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView surname;
        public TextView is_vip;
        public TextView is_participant;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.name_passport);
            surname = itemView.findViewById(R.id.surname_passport);
            is_vip = itemView.findViewById(R.id.VIP_passport);
            is_participant = itemView.findViewById(R.id.type_passport);

            View item = itemView.findViewById(R.id.passport_card);
            if (item == null) return;

            item.setOnClickListener(v -> {
                if(listener != null)
                {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passport_card, parent, false);
        ItemViewHolder item = new ItemViewHolder(v, _clickListener);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        PassportItem currentItem = _list.get(position);

        holder.name.setText(currentItem.get_name());
        holder.surname.setText(currentItem.get_surname());
        holder.is_vip.setText(currentItem.get_is_VIP().toString());
        holder.is_participant.setText(currentItem.get_is_participant().toString());
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

}
