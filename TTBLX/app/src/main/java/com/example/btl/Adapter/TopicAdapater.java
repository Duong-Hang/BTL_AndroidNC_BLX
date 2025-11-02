package com.example.btl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.dscauhoi;
import com.example.btl.Model.Topic;

import java.util.List;

public class TopicAdapater extends  RecyclerView.Adapter<TopicAdapater.TopicViewHolder> {

    private final List<Topic> topicList;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(Topic topic);
    }

    // Hàm set listener từ bên ngoài (MainActivity)
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public  TopicAdapater(Context context, List<Topic> topicList){
        this.context=context;
        this.topicList=topicList;
    }
    @NonNull
    @Override
    public TopicAdapater.TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapater.TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        if (topic == null) {
            Log.w("TopicAdapter", "Topic tại vị trí " + position + " là null");
            return;
        }

        // Đặt tên nhóm câu hỏi
        String topicName = topic.getTen_nhom_cau_hoi();
        holder.tvTopicName.setText(topicName != null ? topicName : "Không có tên");

        // Đặt số câu hỏi
        holder.TopicCounnt.setText(topic.getSo_cau_hoi() + " câu");

        // Lấy và đặt ảnh từ resource ID
        String imageName = topic.getHinh_anh();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = context.getResources().getIdentifier(
                    imageName, "drawable", context.getPackageName());
            if (imageResId != 0) {
                holder.imgTopic.setImageResource(imageResId);
            } else {
                Log.w("TopicAdapter", "Không tìm thấy drawable: " + imageName);
                holder.imgTopic.setImageResource(android.R.drawable.ic_menu_help); // Ảnh mặc định
            }
        } else {
            Log.w("TopicAdapter", "Tên ảnh rỗng tại: " + topicName);
            holder.imgTopic.setImageResource(android.R.drawable.ic_menu_help); // Ảnh mặc định
        }
        // Gọi click listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(topic);
            }
            //truyen du lieu sang cac trang
            Topic selectedTopic = topicList.get(position); // Lấy đúng kiểu
            String tenNhom = selectedTopic.getTen_nhom_cau_hoi();
            Intent intent = new Intent(context, dscauhoi.class);
            intent.putExtra("ten_nhom_cau_hoi",topic.getTen_nhom_cau_hoi());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return topicList != null ? topicList.size() : 0; // Tránh crash nếu topics null
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopicName,TopicCounnt;
        ImageView imgTopic;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopicName = itemView.findViewById(R.id.tv_topic_name);
           imgTopic=itemView.findViewById(R.id.img_topic);
           TopicCounnt=itemView.findViewById(R.id.tvQuestionCount);
        }
    }
}
