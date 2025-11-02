package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl.Fragment.MenuFragment;
import com.example.btl.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class thithu extends AppCompatActivity {

    Button btnstart;
    FirebaseFirestore db;
    List<Question> ds;
    List<Question> exam=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thithu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //them memu
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        //-------------------
        db=FirebaseFirestore.getInstance();
        ds=new ArrayList<>();
        GetQuestion();
        //btn start
        btnstart=findViewById(R.id.btnstart);
        btnstart.setOnClickListener(view -> {
            //truyen dl
            Intent intent=new Intent(thithu.this,ExeamDetail.class);
            intent.putExtra("dsdethi",new ArrayList<>(exam));
            startActivity(intent);
        });
    }
    //lay tat ca du lieu
    private void GetQuestion(){
        db.collection("600_cau_hoi_A1")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ds.clear();
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot q: task.getResult()) {
                                Question  question=q.toObject(Question.class);
                                ds.add(question);
                            }
                            Log.d("Exam", "✅ Tải thành công: " + ds.size() + " câu");

                            // Sau khi tải xong => tạo đề thi
                            List<Question> exam = CreateExam(ds);
                            Log.d("Exam", "Đề thi có " + exam.size() + " câu.");
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Exam", "Lỗi khi tải dữ liệu");
                    }
                });
    }
    //tao ra cac de
    private  List<Question> CreateExam(List<Question> ds){
        List<Question> diemliet=new ArrayList<>();
        List<Question> quy_dinh_chung=new ArrayList<>();
        List<Question> xe_gan_may=new ArrayList<>();
        List<Question> sa_hinh=new ArrayList<>();
        List<Question> bienbao=new ArrayList<>();
        List<Question> van_hoa_dao_duc=new ArrayList<>();
        List<Question> lan_duong=new ArrayList<>();

       for(Question q:ds){
           switch (q.getTenNhomCauHoi()){
               case "Câu điểm liệt":
                   diemliet.add(q);
                   break;
               case "Quy định chung và quy tắc giao thông":
                   quy_dinh_chung.add(q);
                   break;
               case "Biển báo giao thông":
                   bienbao.add(q);
                   break;
               case "Vạch kẻ đường":
                   lan_duong.add(q);
                   break;
               case "Xử lý tình huống giao thông (Sa hình)":
                   sa_hinh.add(q);
                   break;
               case "Quy tắc đối với xe mô tô, xe gắn máy":
                   xe_gan_may.add(q);
                   break;
               case "Văn hóa đạo đức người lái xe":
                   van_hoa_dao_duc.add(q);
                   break;
           }

       }
        //  Chọn ngẫu nhiên theo yêu cầu
        addRandomQuestions(exam, diemliet, 1);
        addRandomQuestions(exam, quy_dinh_chung, 7);
        addRandomQuestions(exam, sa_hinh, 6);
        addRandomQuestions(exam, xe_gan_may, 1);
        addRandomQuestions(exam, van_hoa_dao_duc, 1);
        addRandomQuestions(exam, lan_duong, 1);
        addRandomQuestions(exam, bienbao, 8);

        // Trộn toàn bộ đề cho ngẫu nhiên thứ tự
        Collections.shuffle(exam);

        return exam;
    }
    private void addRandomQuestions(List<Question> target, List<Question> source, int count) {
        if (source == null || source.isEmpty()) return;

        Collections.shuffle(source);
        if (source.size() >= count) {
            target.addAll(source.subList(0, count));
        } else {
            target.addAll(source); // nếu thiếu câu, thêm tất cả
        }
    }

}