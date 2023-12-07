package com.longtv.zappy.common.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.mediaplayer.MediaPlayerFragment;
import com.longtv.zappy.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentMediaAdapter extends RecyclerView.Adapter<ContentMediaAdapter.ViewHolder> {
    private Context context;
    private boolean isBanner;

    public ContentMediaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContentMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageUtils.loadImageCorner(context, holder.ivCoverImage, "https://www.vintagemovieposters.co.uk/wp-content/uploads/2021/03/IMG_1741-scaled.jpeg", 18);

        holder.ivCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Media");
                HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_media)
        ImageView ivCoverImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
