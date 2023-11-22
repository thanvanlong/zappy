package com.longtv.zappy.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentBannerAdapter extends RecyclerView.Adapter<ContentBannerAdapter.ViewHolder> {
    private Context context;

    public ContentBannerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContentBannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentBannerAdapter.ViewHolder holder, int position) {
        ImageUtils.loadImageCorner(context, holder.ivNews, "https://assets-prd.ignimgs.com/2022/07/05/littlewitch-1657042084400.jpg", 18);

//        holder.ivNews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Click banner", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_news)
        ImageView ivNews;
        View rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }
}
