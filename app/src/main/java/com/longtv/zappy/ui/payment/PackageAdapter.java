package com.longtv.zappy.ui.payment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;
import com.longtv.zappy.network.dto.PackagePayment;
import com.longtv.zappy.ui.HomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private List<PackagePayment> paymentList;
    private int pos = 0;

    public PackageAdapter(List<PackagePayment> paymentList) {
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package_payment, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder holder, int position) {
        if (position == pos) {
            holder.containerCard.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.getInstance(), R.color.deep_pink));
        } else {
            holder.containerCard.setCardBackgroundColor(ContextCompat.getColor(HomeActivity.getInstance(), R.color.normal_gray));
        }

        holder.tvPackageName.setText(paymentList.get(position).getGolds() + " xu");

        holder.containerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container_pkg)
        CardView containerCard;
        @BindView(R.id.tv_package_name)
        TextView tvPackageName;
        View mRootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRootView = itemView;
        }
    }
}
