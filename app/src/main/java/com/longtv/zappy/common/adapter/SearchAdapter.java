package com.longtv.zappy.common.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.mediaplayer.MediaPlayerFragment;
import com.longtv.zappy.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Content> mContents = new ArrayList<>();
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        ImageUtils.loadImageCorner(HomeActivity.getInstance(), holder.ivCoverImage, content.getCoverImage(), 10);
        holder.tvTitle.setText(content.getName());
        holder.tvWatchedTimes.setText(content.getViews() + " lượt xem");
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Media");
                content.setRelated(mContents.stream().filter(c -> c.getId() != content.getId()).collect(Collectors.toList()));
                bundle.putSerializable(Constants.DATA, content);
                HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
            }
        });

    }

    public void setmContents(List<Content> mContents) {
        if (mContents != null) {
            this.mContents = mContents;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mContents != null ? mContents.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover_image)
        ImageView ivCoverImage;
        @BindView(R.id.item_root)
        LinearLayout itemRoot;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_watched_times)
        TextView tvWatchedTimes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
