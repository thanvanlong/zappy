package com.longtv.zappy.ui.story;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.ui.HomeActivity;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class StoryDetailFragment extends BaseFragment {
    @BindView(R.id.cl_header)
    ConstraintLayout view;

    @BindView(R.id.btn_read)
    Button btnRead;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_story;
    }

    @Override
    public void onPrepareLayout() {
        Drawable[] layers = new Drawable[1];
        HomeActivity.getInstance().toggleTopBar(8);
        HomeActivity.getInstance().hideBottomBar();

        Glide.with(getViewContext())
                .asBitmap()
                .load("https://static.wikia.nocookie.net/vpop/images/b/bf/Hoang_Thuy_Linh_LINK.jpg")

                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Blurry.with(getViewContext()).radius(4)
//                                .sampling(7)
//                                .color(Color.parseColor("#66575757"))
//                                .from(resource).into(ivBgMusic);

                        Palette.from(resource).generate(palette -> {
                            // Get the dominant color from the Palette
                            int dominantColor = palette.getDominantColor(0xFF000000);
                            ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
                                @Override
                                public Shader resize(int width, int height) {
                                    LinearGradient lg = new LinearGradient(
                                            0,
                                            0,
                                            0,
                                            view.getHeight(),
                                            new int[] {
                                                    dominantColor,
                                                    getResources().getColor(R.color.light_gray), // please input your color from resource for color-4
                                            },
                                            new float[] {0.050f, 0.6f },
                                            Shader.TileMode.CLAMP);
                                    return lg;
                                }
                            };
                            PaintDrawable p = new PaintDrawable();
                            p.setShape(new RectShape());
                            p.setShaderFactory(sf);
                            p.setCornerRadii(new float[] { 5, 5, 5, 5, 0, 0, 0, 0 });
                            layers[0] = (Drawable) p;

                            LayerDrawable composite = new LayerDrawable(layers);
                            view.setBackground(composite);
                            btnRead.setBackground(composite);
                            Toast.makeText(getViewContext(), "" + dominantColor, Toast.LENGTH_SHORT).show();

                        });

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.e("anth", "onLoadCleared: check");
                    }
                });


    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }
}
