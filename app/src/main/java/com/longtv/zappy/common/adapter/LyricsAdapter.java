package com.longtv.zappy.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtv.zappy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.ViewHolder> {
    private List<String> lyrics = new ArrayList<>();
    @NonNull
    @Override
    public LyricsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lyric, parent, false);
        String lyric = "Em có gì đâu ngoài những vết thương sâu\n" +
                "Yêu bao nhiêu lâu mà vẫn cứ thấy đau\n" +
                "Gieo bao tương tư nặng ôm những nỗi buồn\n" +
                "Em trao tim em rồi đem bán linh hồn\n" +
                "Để đổi lấy đôi phút bình yên (bình yên)\n" +
                "Dù nước mắt ướt gối hằng đêm (hằng đêm)\n" +
                "Mà em ơi hi sinh vậy có xứng đáng (vậy có xứng đáng)\n" +
                "Vết bầm trên người em nhiều thêm (nhiều thêm)\n" +
                "Chẳng có lấy một ngày ấm êm (ấm êm)\n" +
                "Chỉ còn tai ương thâu đêm\n" +
                "Không còn tương lai đâu em\n" +
                "Em ơi dừng lại khi nắng đã phai dừng lại khi em bước sai\n" +
                "Dừng lại khi chẳng có ai biết em vẫn đang tồn tại\n" +
                "Em ơi đừng làm lệ vương khoé mi đừng làm thân xác úa si\n" +
                "Tình cảm xin cất bước đi chớ lưu luyến thêm làm gì cố yêu được chi\n" +
                "Chơi đùa chơi đùa thôi à\n" +
                "Xin đừng trêu đùa tôi mà\n" +
                "Đừng giam lòng tin vào nơi vực sâu dựng xây niềm đau thành ngôi nhà luôn là luôn là tôi ya\n" +
                "Đau lòng nhưng mà thôi ya\n" +
                "Thiên hà rơi tả tơi hmm tim là nơi lả lơi\n" +
                "Họ lại xem tôi như con cờ đạp đổ tương lai tôi mong chờ ya\n" +
                "Sai vì tôi mộng mơ vì tình yêu nơi tôi tôn thờ\n" +
                "Tình tàn tình vỡ tan trở về với khuôn mặt đáng thương\n" +
                "Nhìn hành trình dở dang chê bai hay những lời tán dương\n" +
                "Lệ mang sầu đau trên những vết son\n" +
                "Người thương em xưa giờ đây chẳng còn\n" +
                "Đào sương hoa mơ em còn bỡ ngỡ\n" +
                "Thời gian thoi đưa tim em tan vỡ\n" +
                "Thương khi em yêu có đâu ngờ\n" +
                "Trăng soi thân em cứ xác xơ\n" +
                "Ở đâu người em nhớ\n";
        lyrics.addAll(Arrays.asList(lyric.split("\n")));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricsAdapter.ViewHolder holder, int position) {
        holder.tvLyric.setText(lyrics.get(position));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_lyric)
        TextView tvLyric;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
