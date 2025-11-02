package com.example.btl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Model.Result;
import com.example.btl.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final List<Result> ds;
    private final Context context;


    public HistoryAdapter(Context context, List<Result> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ls, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        Result result=ds.get(position);
        holder.tv_examName.setText(result.getMa_de());
        holder.tv_exam_time.setText("Thời gian: "+result.getThoi_gian_hoan_thanh()+" giây");
        holder.tv_exam_date.setText("Ngày thi: "+result.getNgay_lam());
        holder.tv_exam_score.setText(result.getSoCauDung()+"/"+result.getTong_so_cau());
        holder.tv_status.setText(result.getKetQua());

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_examName,tv_exam_time,tv_exam_date,tv_exam_score,tv_status;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_examName=itemView.findViewById(R.id.tv_examName);
            tv_exam_time=itemView.findViewById(R.id.tv_exam_time);
            tv_exam_date=itemView.findViewById(R.id.tv_exam_date);
            tv_exam_score=itemView.findViewById(R.id.tv_exam_score);
            tv_status=itemView.findViewById(R.id.tv_status);
        }
    }
}
