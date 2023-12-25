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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.longtv.zappy.R;
import com.longtv.zappy.base.BaseFragment;
import com.longtv.zappy.base.BasePresenter;
import com.longtv.zappy.common.Constants;
import com.longtv.zappy.network.dto.Chapter;
import com.longtv.zappy.network.dto.Content;
import com.longtv.zappy.network.dto.ContentType;
import com.longtv.zappy.network.dto.DataListDTO;
import com.longtv.zappy.ui.HomeActivity;
import com.longtv.zappy.utils.ImageUtils;
import com.longtv.zappy.utils.StringUtils;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

public class StoryDetailFragment extends BaseFragment<HomeBoxStoryPresenter, HomeActivity> implements HomeBoxStoryView {
    @BindView(R.id.cl_header)
    ConstraintLayout view;

    @BindView(R.id.btn_read)
    Button btnRead;
    @BindView(R.id.iv_book_image)
    ImageView ivBookImage;
    @BindView(R.id.tv_views)
    TextView tvViews;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.rcv_content)
    RecyclerView rcvContent;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_story;
    }

    @Override
    public void onPrepareLayout() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Content content = (Content) bundle.getSerializable(Constants.DATA);
            Drawable[] layers = new Drawable[1];
            HomeActivity.getInstance().toggleTopBar(8);
            HomeActivity.getInstance().hideBottomBar();
            tvViews.setText(content.getViews() + " views");
            tvLike.setText(0 + " likes");
            tvOverview.setText(content.getDesc());
            tvTitle.setText(content.getName());
            tvAuthor.setText(StringUtils.getAuthors(content.getAuthors()));
            getPresenter().getChapter(content.getId() + "");
            Glide.with(getViewContext())
                    .asBitmap()
                    .load(content.getCoverImage())

                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            ivBookImage.setImageBitmap(resource);
                            Palette.from(resource).generate(palette -> {
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
                                btnRead.setBackgroundColor(dominantColor);
                                Toast.makeText(getViewContext(), "" + dominantColor, Toast.LENGTH_SHORT).show();

                            });

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            Log.e("anth", "onLoadCleared: check");
                        }
                    });
        }
    }

    private void loadChapter() {

    }

    @Override
    public HomeBoxStoryPresenter onCreatePresenter() {
        return new HomeBoxStoryPresenterImpl(this);
    }

    @Override
    public void onLoadGenreSuccess(DataListDTO<ContentType> data) {

    }

    @Override
    public void onLoadChapterSuccess(DataListDTO<Chapter> data) {
        rcvContent.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        Chapter chapter = new Chapter();
        chapter.setName("Chapter 1");
        data.getResults().add(chapter);
        rcvContent.setAdapter(new HomeBoxChapterAdapter(data.getResults()));
    }
}
