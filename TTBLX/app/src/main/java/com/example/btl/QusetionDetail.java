package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl.Adapter.QuestionDetailAdapter;
import com.example.btl.Fragment.QuestionDetailFragment;
import com.example.btl.Model.Question;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QusetionDetail extends AppCompatActivity {

    QuestionDetailAdapter adapter;
    QuestionDetailFragment fragment;
    ViewPager2 pager1;
    ImageButton ql1;
    TabLayout tabLayout1;
    List<Question> ds;
    int index=0;
    private Question q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qusetion_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        pager1=findViewById(R.id.Page1);
        ql1=findViewById(R.id.ql1);
        tabLayout1=findViewById(R.id.Tablayout1);
        //------khai bao--------
        ds=new ArrayList<>();
        fragment=new QuestionDetailFragment();

        //lay danh sach tu intent
        ds = (List<Question>) getIntent().getSerializableExtra("dscauhoi");
        index = getIntent().getIntExtra("index",0);
        q=ds.get(index);
        if(q==null){
            Log.e( "Firestore", "Lỗi khi tải dữ liệu");
        }
        if (ds == null || ds.isEmpty()) {
            Log.e( "Firestore", "Lỗi khi tải dữ liệu");
            finish();
            return;
        }
        if(ds!=null&&q!=null){
            Log.d("Firestore", "Số câu hỏi nhận được: " + ds.size());
        }
        //hien thi
        adapter=new QuestionDetailAdapter(this,ds,true);
        pager1.setAdapter(adapter);
        // Kết nối TabLayout và ViewPager2
        new TabLayoutMediator(tabLayout1, pager1, (tab, tabPosition) -> {
            Question question = ds.get(tabPosition);
            tab.setText("Câu " + (tabPosition+1));
        }).attach();
        // Tùy chọn: đặt câu hỏi bắt đầu từ index nào
        pager1.setCurrentItem(index, false);
        //------------------Quay lai trng danh sach-----------------------------
        ql1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        /*
         //--------bam nut return
        btntrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index>0){
                    index--;
                    hienthicauhoi();
                }
                else{
                    tvAnsw.setText("Đang ở câu đầu");
                }
            }
        });
        //--------bam nut next
        btnsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<ds.size()-1){
                    index++;
                    hienthicauhoi();
                }
                else{
                    tvAnsw.setText("Đang ở câu cuối");
                }
            }
        });
         */



    }


}