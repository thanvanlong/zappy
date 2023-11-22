package com.longtv.zappy.ui.story;

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
import com.longtv.zappy.ui.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxStoryAdapter extends RecyclerView.Adapter<HomeBoxStoryAdapter.ViewHolder> {
    @NonNull
    @Override
    public HomeBoxStoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxStoryAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText("Stories");
        holder.rcvContent.setLayoutManager(new LinearLayoutManager(HomeActivity.getInstance(),  LinearLayoutManager.HORIZONTAL, false));
        ContentAdapter contentAdapter = new ContentAdapter();
        holder.rcvContent.setAdapter(contentAdapter);
        holder.rcvContent.addItemDecoration(new HorizontalItemDecoration(30));
    }

    @Override
    public int getItemCount() {
        return 5;
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
