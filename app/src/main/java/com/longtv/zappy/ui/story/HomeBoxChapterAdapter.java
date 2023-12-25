package com.longtv.zappy.ui.story;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.music.detail.HomeBoxMusicPlayerFragment;
import com.longtv.zappy.utils.ImageUtils;
import com.longtv.zappy.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxChapterAdapter extends RecyclerView.Adapter<HomeBoxChapterAdapter.ViewHolder> {
    private List<Chapter> mContents = new ArrayList<>();

    public HomeBoxChapterAdapter(List<Chapter> mContents) {
        this.mContents = mContents;
    }

    @NonNull
    @Override
    public HomeBoxChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxChapterAdapter.ViewHolder holder, int position) {
        Chapter content = mContents.get(position);
        if (!content.getName().isEmpty()) {
            holder.tvTitle.setText(content.getName());
        }
        holder.tvAuthor.setText("Tập " + content.getChap());
        holder.tvPublishedDate.setText(content.getCreatedAt());

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Media");
                bundle.putString(Constants.DATA, new Gson().toJson(content));
                HomeActivity.getInstance().addOrReplaceFragment(new ReadingStoryFragment(), bundle, true, ReadingStoryFragment.class.getSimpleName());
            }
        });
    }

    public void setmContents(List<Content> mContents) {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_published_date)
        TextView tvPublishedDate;

        View mRootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }
    }
}
