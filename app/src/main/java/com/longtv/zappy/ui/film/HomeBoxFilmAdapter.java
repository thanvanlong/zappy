package com.longtv.zappy.ui.film;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxFilmAdapter extends RecyclerView.Adapter<HomeBoxFilmAdapter.ViewHolder> {
    private Context context;
    private List<ContentType> contentTypes = new ArrayList<>();

    public HomeBoxFilmAdapter(Context context, List<ContentType> contentTypes) {
        this.context = context;
        this.contentTypes = contentTypes;
    }

    @NonNull
    @Override
    public HomeBoxFilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_content_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxFilmAdapter.ViewHolder holder, int position) {
        ContentMediaAdapter contentMediaAdapter = new ContentMediaAdapter(HomeActivity.getInstance());
        holder.rcvMedia.setLayoutManager(new GridLayoutManager(HomeActivity.getInstance(), 2));
        holder.rcvMedia.setAdapter(contentMediaAdapter);
    }

    @Override
    public int getItemCount() {
        return contentTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rcy_media)
        RecyclerView rcvMedia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
