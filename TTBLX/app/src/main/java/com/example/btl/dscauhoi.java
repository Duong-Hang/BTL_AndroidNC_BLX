package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Adapter.QuestionAdapter;
import com.example.btl.Model.Question;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class dscauhoi extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter questionAdapter;
    List<Question> lstquestion;
    FirebaseFirestore db;
    ImageButton ql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dscauhoi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView=findViewById(R.id.listquestion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstquestion=new ArrayList<>();
        questionAdapter=new QuestionAdapter(this,lstquestion);
        recyclerView.setAdapter(questionAdapter);
        //hien thi
        loadds();
        //su kien click cac item
        questionAdapter.setOnItemClickListener((question,position)-> {
            Intent intent = new Intent(dscauhoi.this, QusetionDetail.class);
            intent.putExtra("dscauhoi",new ArrayList<>(lstquestion));
            intent.putExtra("index", position);
            startActivity(intent);
        });
        //su kien quay lai trang chu
        ql=findViewById(R.id.ql);
        ql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent=new Intent(dscauhoi.this,MainActivity.class);
                startActivity(myintent);
            }
        });

    }
    private  void loadds(){
        String category=getIntent().getStringExtra("ten_nhom_cau_hoi");
        db=FirebaseFirestore.getInstance();
        db.collection("600_cau_hoi_A1")
                .whereEqualTo("category",category)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        lstquestion.clear();
                        for (QueryDocumentSnapshot q : task.getResult()) {
                            Question question = q.toObject(Question.class);
                            lstquestion.add(question);
                        }


                        Log.d("Firestore", "Số câu hỏi nhận được: " + lstquestion.size());
                        questionAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.e("Firestore", "Lỗi khi tải dữ liệu", task.getException());
                    }

                });

    }

}