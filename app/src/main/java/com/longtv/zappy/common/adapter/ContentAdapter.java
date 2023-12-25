package com.longtv.zappy.common.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.story.StoryDetailFragment;
import com.longtv.zappy.utils.CompatibilityUtils;
import com.longtv.zappy.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private List<Content> contents = new ArrayList<>();

    public ContentAdapter(List<Content> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(HomeActivity.getInstance()).inflate(R.layout.item_story, parent, false);
        int width = CompatibilityUtils.getWidthContentItem(HomeActivity.getInstance());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.ViewHolder holder, int position) {
        Content content = contents.get(position);
        Log.e("anth", "onBindViewHolder: " + content.getId());
        ImageUtils.loadImageCorner(HomeActivity.getInstance(), holder.ivCoverImage, content.getCoverImage(), 18);
        holder.tvTitle.setText(content.getName());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Story");
                bundle.putSerializable(Constants.DATA, content);
                HomeActivity.getInstance().addOrReplaceFragment(new StoryDetailFragment(), bundle, true, StoryDetailFragment.class.getSimpleName());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contents == null)
            return 0;
        else
            return contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover_image)
        ImageView ivCoverImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        View rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }
}
