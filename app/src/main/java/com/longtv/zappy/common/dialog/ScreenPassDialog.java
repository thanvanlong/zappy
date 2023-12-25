package com.longtv.zappy.common.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.longtv.zappy.R;
import com.longtv.zappy.common.view.MyPasswordTransformationMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class ScreenPassDialog extends DialogFragment {
    @BindView(R.id.edt_pass_screen)
    EditText edtPass;
    @BindView(R.id.btn_next)
    Button btnNext;
    private ItemClickListener listener;

    public void setListener (ItemClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return inflater.inflate(R.layout.fragment_dialog_screen_pass, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1000);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        edtPass.setTransformationMethod(new MyPasswordTransformationMethod());
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    edtPass.clearFocus();
                    listener.onSuccess(edtPass.getText().toString());
                }
            }
        });
    }

    @OnTextChanged(R.id.edt_pass_screen)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 4) {
            btnNext.setEnabled(true);
        }
    }

    public interface ItemClickListener {
        void onSuccess(String pass);
    }
}
