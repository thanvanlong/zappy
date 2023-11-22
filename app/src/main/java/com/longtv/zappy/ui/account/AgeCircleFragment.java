package com.longtv.zappy.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longtv.zappy.R;

public class AgeCircleFragment extends Fragment {
    private View mView;
    public AgeCircleFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String age = (String) bundle.getString("age");

        mView = inflater.inflate(R.layout.fragment_age_circle, container, false);
        TextView textView = mView.findViewById(R.id.tv_age);
        textView.setText(age);
        return mView;
    }
}