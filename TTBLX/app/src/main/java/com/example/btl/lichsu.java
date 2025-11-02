package com.example.btl;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Adapter.HistoryAdapter;
import com.example.btl.Database.ResultDao;
import com.example.btl.Fragment.MenuFragment;
import com.example.btl.Model.Result;

import java.util.ArrayList;
import java.util.List;

public class lichsu extends AppCompatActivity {
    TextView tvHistoryCount;
    ImageButton btndelete;
    RecyclerView recyclerViewHistory;
    HistoryAdapter adapter;
    List<Result> ds;
    ResultDao resultDao;
    Result q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lichsu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        tvHistoryCount=findViewById(R.id.tvHistoryCount);
        btndelete=findViewById(R.id.btndelete);
        recyclerViewHistory=findViewById(R.id.recyclerViewHistory);
        //khai bao
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        ds=new ArrayList<>();
        resultDao=new ResultDao(this);
        ds=resultDao.getsds();
        //Them menu vào activy
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        //set text
        tvHistoryCount.setText("Số lượng lịch sử thi: "+ds.size());
        //adapter
        adapter=new HistoryAdapter(this,ds);
        recyclerViewHistory.setAdapter(adapter);
        //xoa
        btndelete.setOnClickListener(view -> {
           showSubmitConfirmationDialog();
        });

    }
    //hien thi dialog
    private void showSubmitConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    long kq=resultDao.delete(); // đồng ý
                    if(kq==-1){
                        Toast.makeText(this, "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        // Cập nhật lại giao diện ngay sau khi xóa
                        ds.clear();  // xóa danh sách hiện tại trong bộ nhớ
                        adapter.notifyDataSetChanged(); // thông báo cho RecyclerView cập nhật lại
                        tvHistoryCount.setText("Số lượng lịch sử thi: 0"); // cập nhật số lượng
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss(); //  hủy
                })
                .setCancelable(true) // cho bấm ngoài để tắt dialog
                .show();
    }
}