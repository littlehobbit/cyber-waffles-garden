package com.example.taganrogdefender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public TextView is_vip;
        public TextView is_guest;
        public TextView is_rent;
        public TextView size;
        public TextView type;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.name_passport);
            is_vip = itemView.findViewById(R.id.VIP_passport);
            is_guest = itemView.findViewById(R.id.is_guest_passport);
            is_rent = itemView.findViewById(R.id.is_rent_passport);
            size = itemView.findViewById(R.id.size_passport);
            type = itemView.findViewById(R.id.type_passport);

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

        holder.name.setText(currentItem.get_name() + " " + currentItem.get_surname());

        if(currentItem.get_is_VIP())
        {
            holder.is_vip.setText("да");
        }
        else {
            holder.is_vip.setText("нет");
        }

        if(currentItem.get_is_guest())
        {
            holder.is_guest.setText("Гость");
            holder.type.setText("Отсутствует");
        }
        else {
            holder.is_guest.setText("Участник");
            if(currentItem.get_type() == PassportItem.type_of_participant.Sport)
            {
                holder.type.setText("Спорт");
            }
            if (currentItem.get_type() == PassportItem.type_of_participant.Reconstruction){
                holder.type.setText("Реконструктор");
            }

        }

        if(currentItem.get_is_rent()) {
            holder.is_rent.setText("да");
            holder.size.setText(String.valueOf(currentItem.get_size()));

        } else {
            holder.is_rent.setText("нет");
            holder.size.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

}
