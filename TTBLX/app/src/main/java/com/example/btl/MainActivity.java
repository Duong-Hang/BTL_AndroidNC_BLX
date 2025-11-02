package com.example.btl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Adapter.TopicAdapater;
import com.example.btl.Fragment.MenuFragment;
import com.example.btl.Model.Question;
import com.example.btl.Model.Topic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TopicAdapater adapter;
    private List<Topic> topicList;
    private FirebaseFirestore db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Them menu vào activy
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        //
        recyclerView=findViewById(R.id.recyclerViewTopics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicList=new ArrayList<>();
        adapter=new TopicAdapater(this,topicList);
        recyclerView.setAdapter(adapter);
        //tai danh sach
        Gettopic();
        //truuyen du lieu sang cac trang
        adapter.setOnItemClickListener(new TopicAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(Topic topic) {
                Intent myintent=new Intent(MainActivity.this, dscauhoi.class);
                myintent.putExtra("ten_nhom_cau_hoi",topic.getTen_nhom_cau_hoi());
                startActivity(myintent);
            }
        });

    }
    public void Gettopic() {
        db=FirebaseFirestore.getInstance();
        Topic topic=new Topic();
        db.collection("600_cau_hoi_A1")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Map<String, Integer> categoryCountMap = new HashMap<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                String category = doc.getString("category");
                                if(category!=null && !category.isEmpty()){
                                    int count=categoryCountMap.getOrDefault(category,0);
                                    categoryCountMap.put(category,count+1);
                                };

                            }
                            topicList.clear();
                            for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
                                String categories = entry.getKey();
                                int count = entry.getValue();

                                // Xác định tên ảnh theo category
                                String imageName;
                                switch (categories) {
                                    case "Xử lý tình huống giao thông (Sa hình)":
                                        imageName = "sa_hinh"; // tên file drawable: sahinh.png
                                        break;
                                    case "Quy tắc đối với xe mô tô, xe gắn máy":
                                        imageName = "xe_gan_may"; // tên file drawable: luat.png
                                        break;
                                    case "Biển báo giao thông":
                                        imageName = "bienbao"; // tên file drawable: bienbao.png
                                        break;

                                    case "Văn hóa đạo đức người lái xe":
                                        imageName="van_hoa_dao_duc";
                                        break;
                                    case "Vạch kẻ đường":
                                        imageName="lan_duong";
                                        break;
                                    case "Câu điểm liệt":
                                        imageName="diem_liet";
                                        break;
                                    case "Quy định chung và quy tắc giao thông":
                                        imageName="quy_dinh_chung";
                                        break;

                                    default:
                                        imageName = "default_image"; // ảnh mặc định nếu category không có mapping
                                        break;
                                }
                                topicList.add(new Topic(categories,count,imageName));
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Lỗi khi tải dữ liệu", e);
                    }
                });

    }
}