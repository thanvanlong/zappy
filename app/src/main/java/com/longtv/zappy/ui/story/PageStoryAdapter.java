package com.longtv.zappy.ui.story;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longtv.zappy.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PageStoryAdapter extends RecyclerView.Adapter<PageStoryAdapter.PageViewHolder>{

    private ArrayList<PageStory> pages;

    public PageStoryAdapter(ArrayList<PageStory> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_page_story, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        PageStory page = pages.get(position);
        holder.imageView.setImageResource(page.getImage());
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class PageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_story);
        }
    }
}
