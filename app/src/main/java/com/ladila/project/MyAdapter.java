package com.ladila.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ladila.project.Pojo.DBPojo.DBResult;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<DBResult> dbResults = new ArrayList<>();
    Context context;
    public interface OnClickListener{
        void onClick(DBResult dbResult);
    }
    OnClickListener onClickListener;
    public MyAdapter(List<DBResult> dbResults, Context context,OnClickListener onClickListener) {
        this.dbResults = dbResults;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recyclerview_item,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        try {
            holder.tvId.setText("ID : "+dbResults.get(position).getId().toString());
            holder.tvTitle.setText(dbResults.get(position).getOriginalTitle());

            Glide.with(context)
                    .load("http://api.themoviedb.org"+dbResults.get(position).getPosterPath())
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .into(holder.ivIcon);

            holder.myLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(dbResults.get(position));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dbResults.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        LinearLayout myLL;
        TextView tvTitle,tvId;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvId = itemView.findViewById(R.id.tvId);
            myLL = itemView.findViewById(R.id.myLL);

        }
    }
}
