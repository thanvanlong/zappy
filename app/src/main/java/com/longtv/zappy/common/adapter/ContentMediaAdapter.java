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
        content.setRelated(mContents.stream().filter(it -> it.getId() != content.getId()).collect(Collectors.toList()));

        if (!content.isAccess()) {
            ImageUtils.loadImageCornerBlur(context, holder.ivCoverImage, content.getCoverImage(), 18);
            holder.ivPlay.setImageResource(R.drawable.ic_lock_menu);
        } else {
            ImageUtils.loadImageCorner(context, holder.ivCoverImage, content.getCoverImage(), 18);
        }

        holder.ivCoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.isAccess()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.TOOL_BAR, "Media");
                    bundle.putSerializable(Constants.DATA, content);
                    Fragment topFragment = HomeActivity.getInstance().getSupportFragmentManager().findFragmentById(R.id.container_fragment);
                    if (topFragment instanceof MediaPlayerFragment) {
                        Toast.makeText(context, "Đang xem nội dung này " + content.getName(), Toast.LENGTH_SHORT).show();
                        MediaPlayerFragment.getInstance().setArguments(bundle);
                        MediaPlayerFragment.getInstance().onPrepareLayout();
                    } else {
                        HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
                    }
//                    HomeActivity.getInstance().addOrReplaceFragment(new MediaPlayerFragment(), bundle, true, MediaPlayerFragment.class.getSimpleName());
                } else {
                    InfoYesNoDialog infoYesNoDialog = new InfoYesNoDialog();
                    infoYesNoDialog.init(context, "Nội dung yêu cầu trả phí để có thể trải nghiệm. Vui lòng mua nội dung để xem");
                    infoYesNoDialog.setListener(new InfoYesNoDialog.ItemClickListener() {
                        @Override
                        public void btnYesClick() {
                            if (content.getGolds() > 20) {
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.TOOL_BAR, "Payment");
                                HomeActivity.getInstance().addOrReplaceFragment(new PackagePaymentFragment(), bundle, true, PackagePaymentFragment.class.getSimpleName());
                            } else {
                                InfoYesNoDialog dialog = new InfoYesNoDialog();
                                dialog.init(context, "Số dư không đủ. Vui lòng mua thêm vàng để thanh toán");
                                dialog.setListener(new InfoYesNoDialog.ItemClickListener() {
                                    @Override
                                    public void btnYesClick() {
                                        Bundle bundle = new Bundle();
                                        bundle.putString(Constants.TOOL_BAR, "Payment");
                                        HomeActivity.getInstance().addOrReplaceFragment(new PackagePaymentFragment(), bundle, true, PackagePaymentFragment.class.getSimpleName());
                                    }

                                    @Override
                                    public void btnNoClick() {

                                    }
                                });
                                dialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
                            }
                        }

                        @Override
                        public void btnNoClick() {

                        }
                    });

                    infoYesNoDialog.show(HomeActivity.getInstance().getSupportFragmentManager(), "");
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
