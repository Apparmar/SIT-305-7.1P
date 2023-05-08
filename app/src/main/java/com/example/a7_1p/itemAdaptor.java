package com.example.a7_1p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a7_1p.model.Item;

import java.util.List;

public class itemAdaptor extends RecyclerView.Adapter<itemAdaptor.viewHolder> {
    List <Item> itemList;
    Context context;
    ItemClickListener clickListener;


    public itemAdaptor(List <Item> itemList, Context context,ItemClickListener clickListener)
    {
        this.itemList = itemList;
        this.context = context;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.itemoverview,parent,false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemHead.setText(item.getName());

        holder.itemHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView itemHead;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemHead = itemView.findViewById(R.id.itemHead);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(Item item);
    }
}
