package com.longtv.zappy.ui.music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxMusicAdapter extends RecyclerView.Adapter<HomeBoxMusicAdapter.ViewHolder> {
    @NonNull
    @Override
    public HomeBoxMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxMusicAdapter.ViewHolder holder, int position) {
        ImageUtils.loadImageOval(HomeActivity.getInstance(), holder.ivCoverImage, "https://leplateau.edu.vn/wp-content/uploads/2023/10/hinh-anh-con-gai-1.jpg");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover_image)
        ImageView ivCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
