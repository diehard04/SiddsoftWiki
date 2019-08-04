package com.example.siddsoftassignmenttask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siddsoftassignmenttask.R;
import com.example.siddsoftassignmenttask.model.WikiModel;

import java.util.List;

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.MyViewHolder> {
    private List<WikiModel> wikiModelList;
    private OnItemClicked onItemClicked;

    public WikiAdapter(List<WikiModel> wikiModels, OnItemClicked onItemClicked) {
        this.wikiModelList = wikiModels;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wiki_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        WikiModel wikiModel = wikiModelList.get(position);
        holder.title.setText(wikiModel.getTitle());
        holder.description.setText(wikiModel.getDescription());
        holder.openWebPage.setText(wikiModel.getEditurl());
        holder.openWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wikiModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, openWebPage;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_tv);
            imageView = (ImageView) view.findViewById(R.id.wiki_iv);
            description = (TextView) view.findViewById(R.id.descrption_tv);
            openWebPage = view.findViewById(R.id.edit_url_tv);
        }
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
}
