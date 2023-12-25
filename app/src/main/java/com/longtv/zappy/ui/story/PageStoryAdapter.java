package com.longtv.zappy.ui.story;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longtv.zappy.R;
import com.longtv.zappy.network.dto.ChapterWrapper;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.ImageUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageStoryAdapter extends RecyclerView.Adapter<PageStoryAdapter.PageViewHolder>{

    private List<ChapterWrapper> pages = new ArrayList<>();

    public PageStoryAdapter(List<ChapterWrapper> pages) {
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

        ChapterWrapper page = pages.get(position);
        ImageUtils.loadImageCorner(HomeActivity.getInstance(), holder.imageView, page.getUrl(), 10);
//        holder.imageView.setImageResource(page.getImage());
    }

    @Override
    public int getItemCount() {
        return pages.size();
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
