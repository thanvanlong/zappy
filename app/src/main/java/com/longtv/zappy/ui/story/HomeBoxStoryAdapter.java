package com.longtv.zappy.ui.story;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.adapter.ContentAdapter;
import com.longtv.zappy.common.view.HorizontalItemDecoration;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxStoryAdapter extends RecyclerView.Adapter<HomeBoxStoryAdapter.ViewHolder> {
    private List<ContentType> contentTypes = new ArrayList<>();

    public HomeBoxStoryAdapter(List<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    @NonNull
    @Override
    public HomeBoxStoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxStoryAdapter.ViewHolder holder, int position) {
        ContentType contentType = contentTypes.get(position);
        holder.tvTitle.setText(contentType.getName());
        holder.rcvContent.setLayoutManager(new LinearLayoutManager(HomeActivity.getInstance(),  LinearLayoutManager.HORIZONTAL, false));
        ContentAdapter contentAdapter = new ContentAdapter(contentType.getComics());
        holder.rcvContent.setAdapter(contentAdapter);
        holder.rcvContent.addItemDecoration(new HorizontalItemDecoration(30));
    }

    @Override
    public int getItemCount() {
        return contentTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rcv_content)
        RecyclerView rcvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
