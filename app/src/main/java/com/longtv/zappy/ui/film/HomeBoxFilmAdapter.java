package com.longtv.zappy.ui.film;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.adapter.ContentMediaAdapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBoxFilmAdapter extends RecyclerView.Adapter<HomeBoxFilmAdapter.ViewHolder> {
    private Context context;
    private Map<String, List<Content>> data = new HashMap<>();
    private List<String> types = new ArrayList<>();
    private List<ContentMediaAdapter> contentMediaAdapters = new ArrayList<>();

    public HomeBoxFilmAdapter(Context context, Map<String, List<Content>> contentTypes) {
        this.context = context;
        this.data = contentTypes;
        types = new ArrayList<>(data.keySet());
    }

    @NonNull
    @Override
    public HomeBoxFilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_content_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBoxFilmAdapter.ViewHolder holder, int position) {
        String type = types.get(position);
        ContentMediaAdapter contentMediaAdapter = new ContentMediaAdapter(context, data.get(types.get(position)));
        contentMediaAdapters.add(contentMediaAdapter);
        holder.rcvMedia.setLayoutManager(new GridLayoutManager(context, 2));
        holder.rcvMedia.setAdapter(contentMediaAdapter);
    }

    public void setData(Map<String, List<Content>> data, String id) {
        this.data = data;
        int pos = types.indexOf(id);
        notifyDataSetChanged();
        contentMediaAdapters.get(pos).setmContents(data.get(id));
        contentMediaAdapters.get(pos).notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
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
