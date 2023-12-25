package com.longtv.zappy.common.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.common.dialog.InfoYesNoDialog;
import com.longtv.zappy.common.dialog.SuccessDialog;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.ui.film.mediaplayer.MediaPlayerFragment;
import com.longtv.zappy.ui.payment.PackagePaymentFragment;
import com.longtv.zappy.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentMediaAdapter extends RecyclerView.Adapter<ContentMediaAdapter.ViewHolder> {
    private Context context;
    private List<Content> mContents = new ArrayList<>();

    public ContentMediaAdapter(Context context) {
        this.context = context;
    }

    public ContentMediaAdapter(Context context, List<Content> mContents) {
        this.context = context;
        this.mContents = mContents;
    }

    @NonNull
    @Override
    public ContentMediaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Content content = mContents.get(position);
        if (!content.isAccess()) {
            ImageUtils.loadImageCornerBlur(context, holder.ivCoverImage, content.getCoverImage(), 18);
            holder.ivPlay.setImageResource(R.drawable.ic_lock_menu);
        } else {
            ImageUtils.loadImageCorner(context, holder.ivCoverImage, content.getCoverImage(), 18);
        }

        holder.ivCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TOOL_BAR, "Media");
                bundle.putSerializable(Constants.DATA, content);
                Fragment topFragment = HomeActivity.getInstance().getSupportFragmentManager().findFragmentById(R.id.container_fragment);
                if (topFragment instanceof MediaPlayerFragment) {
                    MediaPlayerFragment.getInstance().setArguments(bundle);
                    MediaPlayerFragment.getInstance().onPrepareLayout();
                } else {
                    HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
                }
            }
        });
    }

    public void setmContents(List<Content> mContents) {
        this.mContents = mContents;
        notifyDataSetChanged();
        Log.e("anth", "setmContents: " + mContents);
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_media)
        ImageView ivCoverImage;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
