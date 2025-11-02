package com.example.btl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Model.Question;
import com.example.btl.Model.Topic;
import com.example.btl.QusetionDetail;
import com.example.btl.R;
import com.example.btl.dscauhoi;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter< QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(Question question,int position);
    }

    // Hàm set listener từ bên ngoài (MainActivity)
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public QuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cauhoi, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.sttcauhoi.setText("Câu " + (position + 1));
        holder.ndcauhoi.setText(question.getNoiDungCauHoi());
        //xu ly su kien onclick
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(question,position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView ndcauhoi, sttcauhoi;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ndcauhoi = itemView.findViewById(R.id.ndcauhoi);
            sttcauhoi = itemView.findViewById(R.id.sttcauhoi);
        }
    }


}
