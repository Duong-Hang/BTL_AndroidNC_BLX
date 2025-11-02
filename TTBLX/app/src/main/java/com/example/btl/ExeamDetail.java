package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl.Adapter.QuestionAdapter;
import com.example.btl.Adapter.QuestionDetailAdapter;
import com.example.btl.Database.ResultDao;
import com.example.btl.Fragment.QuestionDetailFragment;
import com.example.btl.Model.Question;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExeamDetail extends AppCompatActivity {
    TabLayout Tablayout2;
    ViewPager2 pager2;
    Button submitButton,check;
    TextView tvTimer;
    ImageButton ql1;
    int time=19;
    List<Question> ds;
    QuestionDetailAdapter adapter;
    QuestionDetailFragment fragment;
    int index=0;
    Question q;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 1140000; // 19 minutes
    int correctCount = 0,sai=0,noanswer=0; //so cau dung
    ResultDao resultDao;
    String ketQua,maDe;
    boolean diemliet=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exeam_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        Tablayout2=findViewById(R.id.Tablayout2);
        pager2=findViewById(R.id.pages2);
        submitButton=findViewById(R.id.submitButton);
        tvTimer=findViewById(R.id.tvTimer);
        ql1=findViewById(R.id.ql1);
        //------khai bao--------
        ds=new ArrayList<>();
        fragment=new QuestionDetailFragment();
        resultDao = new ResultDao(this);
        //nhan du lieu
        ds = (List<Question>) getIntent().getSerializableExtra("dsdethi");
        index = getIntent().getIntExtra("index", 0);
        q=ds.get(index);
        if(q==null){
            Log.e( "Firestore", "Lỗi khi tải dữ liệu");
        }
        if (ds == null || ds.isEmpty()) {
            Log.e( "Firestore", "Lỗi khi tải dữ liệu");
            finish();
            return;
        }
        //adapter
        adapter=new QuestionDetailAdapter(this,ds,false);
        pager2.setAdapter(adapter);
        //ket noi tablayout vs pages2
        new TabLayoutMediator(Tablayout2, pager2, (tab, tabPosition) -> {
            Question question = ds.get(tabPosition);
            tab.setText("Câu " + (tabPosition+1));
        }).attach();
        // Tùy chọn: đặt câu hỏi bắt đầu từ index nào
        pager2.setCurrentItem(index, false);
        //nhan nut nop bai
        submitButton.setOnClickListener(view -> {
            showSubmitConfirmationDialog();
                });
        //------------------quay lai
        ql1.setOnClickListener(view -> {
            finish();
        });
    }
    protected void onResume() {
        super.onResume();
        startTimer(); // chạy đếm giờ khi activity hiển thị
    }


    //tinh thoi gian
    private void startTimer() {
        // Đảm bảo timeLeftInMillis được khởi tạo trước khi bắt đầu
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) { // 1000ms = 1s
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;  // Cập nhật thời gian còn lại
                updateTimerText();  // Cập nhật hiển thị đồng hồ
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Hết giờ!");
                submitExam();
            }
        }.start();
    }
    //hien thi dialog
    private void showSubmitConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận nộp bài")
                .setMessage("Bạn có chắc chắn muốn nộp bài không?")
                .setPositiveButton("Nộp bài", (dialog, which) -> {
                    submitExam(); // đồng ý
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss(); //  hủy
                })
                .setCancelable(true) // cho bấm ngoài để tắt dialog
                .show();
    }
   //hien thi tg
   private void updateTimerText() {
       // Tính toán phút và giây từ timeLeftInMillis
       int minutes = (int) (timeLeftInMillis / 1000) / 60;  // Lấy số phút
       int seconds = (int) (timeLeftInMillis / 1000) % 60;  // Lấy số giây còn lại

       // Định dạng lại thời gian để hiển thị dưới dạng mm:ss
       String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
       tvTimer.setText(timeFormatted);  // Hiển thị lên UI
   }
    //cac su kien khi click nop bai
    private void submitExam() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Dừng bộ đếm
        }
        //tong so cau
        int tongSoCau = ds.size();
        //tinh so cau dung
        correctCount = 0;
        for (Question q : ds) {
            if (q.getSelectedAnswer() == null) {
                noanswer++; // chưa trả lời
            } else if (q.getSelectedAnswer().equals(q.getDapAnDung())) {
                correctCount++; // đúng
            } else {
                sai++; // sai
                if(q.getTenNhomCauHoi()!=null && q.getTenNhomCauHoi().equals("Câu điểm liệt ")){
                   diemliet=true;
                }
            }
        }
        // Tinh tgian
        int thoiGianHoanThanhGiay = (int) ((19 * 60 * 1000 - timeLeftInMillis) / 1000); // thời gian đã dùng (ms)
        // Xác định kết quả
        if(diemliet){
            ketQua = "Không đạt(Sai câu điểm liệt)";
            Log.d("KETQUA", "Sai câu điểm liệt → Không đạt");
        }
        else{
            ketQua = (correctCount >= 21) ? "Đạt" : "Không đạt";
            Log.d("KETQUA", "Không sai câu điểm liệt, đúng " + correctCount + " câu");
        }
        //luu ket qua vao databse
        try {
            //--------------------luu ma de
            // Sinh mã kết quả ngẫu nhiên (UUID)
            String maKetQua = java.util.UUID.randomUUID().toString();

           // Tạo mã đề tự động tăng De1, De2, De3,...
            android.content.SharedPreferences prefs = getSharedPreferences("exam_prefs", MODE_PRIVATE);
            int lastDeNumber = prefs.getInt("lastDeNumber", 0);  // nếu chưa có thì là 0
            int newDeNumber = lastDeNumber + 1;
            maDe = "Đề: " + newDeNumber; // ví dụ: De1, De2, De3...
            // Lưu lại số đề mới nhất
            prefs.edit().putInt("lastDeNumber", newDeNumber).apply();
            //------------------------------------------
            String ngayLam = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
            resultDao.insert(this,maKetQua,maDe,tongSoCau,thoiGianHoanThanhGiay,ngayLam,correctCount,ketQua);
        }catch (Exception e){
            Log.e("ExamDetail", "Lỗi khi lưu kết quả: " + e.getMessage());
        }//truyen dl
        Intent intent = new Intent(ExeamDetail.this, kq.class);
        intent.putExtra("MaDe", maDe);
        intent.putExtra("sai",sai);
        intent.putExtra("noanswer",noanswer );
        intent.putExtra("dscauhoi", new ArrayList<>(ds));
        startActivity(intent);

    }
}