package com.example.btl.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Database.ResultDao;
import com.example.btl.Model.Question;
import com.example.btl.Model.Result;
import com.example.btl.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder>  {
    private Context context;
    private List<Question> list;
    private ResultDao resultDao;
    private OnItemClickListener onItemClickListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public interface OnItemClickListener {
        void onItemClick(Question question,int position);
    }
    public void setOnItemClickListener(ResultAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public ResultAdapter(Context context, List<Question> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qustionresult, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ResultViewHolder holder, int position) {
        Question question = list.get(position);
        holder.tvQuestionNumber.setText(String.valueOf(position + 1));
        //hien thi mau theo sai dung hay chau lam
        // Hiển thị màu theo đúng/sai
        if (question.getSelectedAnswer() == null) {
                    holder.tvQuestionNumber.setBackgroundResource(R.color.xam);
        } else if (question.getSelectedAnswer().equals(question.getDapAnDung())) {
            holder.tvQuestionNumber.setBackgroundResource(R.color.dung);
        } else {
            holder.tvQuestionNumber.setBackgroundResource(R.color.sai);
        }
        //xu ly su kien onclick
        /*holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(question,position);
            }
        });*/
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(question,position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionNumber;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestionNumber = itemView.findViewById(R.id.tvQuestionNumber);
        }
    }
    private void Dialoghienquestion(){

    }
}
