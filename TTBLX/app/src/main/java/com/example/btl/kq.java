package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl.Adapter.QuestionAdapter;
import com.example.btl.Adapter.QuestionDetailAdapter;
import com.example.btl.Adapter.ResultAdapter;
import com.example.btl.Database.ResultDao;
import com.example.btl.Model.Question;
import com.example.btl.Model.Result;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class kq extends AppCompatActivity {
    TextView tvExamName,tvExamStatus,tvScore,tvTime;
    TextView tvCorrectCount,tvWrongCount,tvUnansweredCount;
    RecyclerView ViewQuestions;
    Button btnRetry,btnViewHistory;
    List<Question> ds;
    ResultDao resultDao;
    List<Result> kq;
    String maDe;
    ResultAdapter adapter;
    LinearLayout layout_chitiet,layout_list;
    QuestionDetailAdapter detailAdapter;
    ViewPager2 pager3;
    int index=0;
    TabLayout Tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kq);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        tvExamName=findViewById(R.id.tvExamName);
        tvExamStatus=findViewById(R.id.tvExamStatus);
        tvScore=findViewById(R.id.tvScore);
        tvTime=findViewById(R.id.tvTime);
        tvCorrectCount=findViewById(R.id.tv_correct_count);
        tvWrongCount=findViewById(R.id.tv_wrong_count);
        tvUnansweredCount=findViewById(R.id.tv_unanswered_count);
        ViewQuestions=findViewById(R.id.viewQuestions);
        btnRetry=findViewById(R.id.btnRetry);
        btnViewHistory=findViewById(R.id.btnViewHistory);
        layout_chitiet=findViewById(R.id.layout_chitiet);
        layout_list=findViewById(R.id.layout_list);
        pager3=findViewById(R.id.pages3);
        Tablayout=findViewById(R.id.Tablayout);
        //----------khai bao
        resultDao=new ResultDao(this);
        ds=new ArrayList<>();
        kq=new ArrayList<>();

        //nhan du lieu
        maDe=getIntent().getStringExtra("MaDe");
        int sai=getIntent().getIntExtra("sai",0);
        int noanswer=getIntent().getIntExtra("noanswer",0);
        ds=(List<Question>) getIntent().getSerializableExtra("dscauhoi");
        //hien thi thong tin
        if(maDe!=null){
            kq=resultDao.getAllResults(maDe);

            if(kq!=null && !kq.isEmpty()){
                Result result=kq.get(0);//lay lan lam gan nhat
                Log.e("Exam","kq: "+kq.size());
                //set du lieu
                tvExamName.setText(maDe);
                tvExamStatus.setText(result.getKetQua());
                tvScore.setText(result.getSoCauDung()+"/"+result.getTong_so_cau());
                int minutes = result.getThoi_gian_hoan_thanh() / 60;
                int seconds = result.getThoi_gian_hoan_thanh() % 60;
                tvTime.setText(minutes + " phút " + seconds + " giây");
                tvCorrectCount.setText("Đúng: "+result.getSoCauDung());
                tvWrongCount.setText("Sai: "+sai);
                tvUnansweredCount.setText("Chưa làm: "+noanswer);

            }
            else{
                tvExamStatus.setText("Chưa có kết quả cho mã đề này.");
                Log.e("Exam","kq: khong co ket qua");
            }
        }
        //hien thi danh sach cau hoi

       // adapter=new ResultAdapter(this,ds);
        adapter = new ResultAdapter(this, ds, (question, position) -> {
            // Khi nhấn vào từng item
            layout_list.setVisibility(View.GONE);
           layout_chitiet.setVisibility(View.VISIBLE);
            // Hiển thị chi tiết câu hỏi
            detailAdapter=new QuestionDetailAdapter(this,ds,false);
            pager3.setAdapter(detailAdapter);
            pager3.setCurrentItem(ds.indexOf(question), false);
            // Kết nối TabLayout và ViewPager2
            new TabLayoutMediator(Tablayout, pager3, (tab, tabPosition) -> {
                Question q = ds.get(tabPosition);
                tab.setText("Câu " + (tabPosition+1));
            }).attach();
            // Tùy chọn: đặt câu hỏi bắt đầu từ index nào
            index=ds.indexOf(question);
            pager3.setCurrentItem(index, false);
        });

        ViewQuestions.setLayoutManager(new GridLayoutManager(this, 5));//hien thi recyle 5 cot
        ViewQuestions.setAdapter(adapter);



        //thi lai
        btnRetry.setOnClickListener(view ->retryExam());
        //quay lai
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(kq.this, thithu.class);
                    startActivity(i);

            }
        });

    }
    //lam lai de thi
    private void retryExam() {
        if (maDe != null) {
            Intent examIntent = new Intent(kq.this, ExeamDetail.class);
            examIntent.putExtra("ma_de", maDe); // Truyền mã đề thi sang ExamActivity
            startActivity(examIntent);
            finish();
        } else {
            Toast.makeText(this, "Không tìm thấy mã đề thi để làm lại", Toast.LENGTH_SHORT).show();
        }
    }
    //hien thi danh sach ca hoi

}