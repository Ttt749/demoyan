package com.example.demoyan.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoyan.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<String> stringList ;

    public ListAdapter(List<String> list){
        this.stringList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView listText;
        public ViewHolder(View itemView) {
            super(itemView);
            listText = (TextView) itemView.findViewById(R.id.list_text);

        }
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getDrawingCacheBackgroundColor() != Color.BLUE){
                    v.setBackgroundColor(Color.BLUE);
                }else{
                    v.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        String string = stringList.get(position);
        holder.listText.setText(string);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
