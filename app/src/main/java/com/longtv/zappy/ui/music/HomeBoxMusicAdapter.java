package com.longtv.zappy.ui.music;

import android.os.Bundle;
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
import com.longtv.zappy.ui.music.detail.HomeBoxMusicPlayerFragment;
import com.longtv.zappy.utils.ImageUtils;
import com.longtv.zappy.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxMusicAdapter extends RecyclerView.Adapter<HomeBoxMusicAdapter.ViewHolder> {
    private List<Content> mContents = new ArrayList<>();
    @NonNull
    @Override
    public HomeBoxMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxMusicAdapter.ViewHolder holder, int position) {
        Content content = mContents.get(position);
        content.setRelated(mContents.stream().filter(c -> c.getId() != content.getId()).collect(Collectors.toList()));
        ImageUtils.loadImageOval(HomeActivity.getInstance(), holder.ivCoverImage, content.getCoverImage());
        holder.tvTitle.setText(content.getName());
        holder.tvAuthor.setText(StringUtils.getAuthors(content.getAuthors()));
        holder.tvDuration.setText("4m 30s");

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Media");
                bundle.putSerializable(Constants.DATA, content);
                HomeActivity.getInstance().addOrReplaceFragment(new HomeBoxMusicPlayerFragment(), bundle, true, HomeBoxMusicPlayerFragment.class.getSimpleName());
            }
        });
    }

    public void setmContents(List<Content> mContents) {
        this.mContents = mContents;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover_image)
        ImageView ivCoverImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_author)
        TextView tvAuthor;

        View mRootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }
    }
}
