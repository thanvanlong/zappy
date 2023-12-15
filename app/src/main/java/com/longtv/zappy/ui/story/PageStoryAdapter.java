package com.longtv.zappy.ui.story;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longtv.zappy.R;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.ImageUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageStoryAdapter extends RecyclerView.Adapter<PageStoryAdapter.PageViewHolder>{

    private ArrayList<PageStory> pages = new ArrayList<>();

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
//        PageStory page = pages.get(position);
        ImageUtils.loadImageCorner(HomeActivity.getInstance(), holder.imageView, "https://assets-prd.ignimgs.com/2022/07/05/littlewitch-1657042084400.jpg", 10);
//        holder.imageView.setImageResource(page.getImage());
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_story)
        ImageView imageView;
        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            imageView = itemView.findViewById(R.id.iv_story);
        }
    }
}
