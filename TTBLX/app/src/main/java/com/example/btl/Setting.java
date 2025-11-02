package com.example.btl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl.Fragment.MenuFragment;

public class Setting extends AppCompatActivity {
    LinearLayout layout_nen,layout_security,layout_inf,layout_reset;
    ToggleButton toggleButton;
    private SharedPreferences prefs;
    TextView textViewinf;
    ScrollView scrollView_setting,scrollview_security;
    ImageButton btnql;
    int click=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
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
        //anhxa
        layout_nen=findViewById(R.id.layout_nen);
        layout_security=findViewById(R.id.layout_security);
        layout_inf=findViewById(R.id.layout_inf);
        toggleButton=findViewById(R.id.toggleButton);
        textViewinf=findViewById(R.id.textViewinf);
        scrollView_setting=findViewById(R.id.scrollView_setting);
        scrollview_security=findViewById(R.id.scrollview_security);
        btnql=findViewById(R.id.btnql);
        layout_reset=findViewById(R.id.layout_reset);
        //-----------------------------------------------------------------------------
        //xu ly sang tối
        prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);

        // 🔹 Lấy theme đã lưu
        boolean isDarkMode = prefs.getBoolean("isDarkMode", false);

        // 🔹 Áp dụng theme khi mở app
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Chuyển sang chế độ tối
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                prefs.edit().putBoolean("isDarkMode", true).apply();
            } else {
                // Chuyển sang chế độ sáng
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                prefs.edit().putBoolean("isDarkMode", false).apply();
            }
        });
        //----------------------------------------------------------
        layout_security.setOnClickListener(view -> {
                scrollview_security.setVisibility(View.VISIBLE);
                scrollView_setting.setVisibility(View.GONE);
        });
        layout_inf.setOnClickListener(view -> {
            //xu ly
            if(click==1 || click%3==0){
                textViewinf.setVisibility(View.VISIBLE);
            }
            else{
                textViewinf.setVisibility(View.GONE);
            }
            click++;
        });
        btnql.setOnClickListener(view -> {
            scrollview_security.setVisibility(View.GONE);
            scrollView_setting.setVisibility(View.VISIBLE);
        });
        layout_reset.setOnClickListener(view -> {
            showSubmitConfirmationDialog();
        });

    }
    private void showSubmitConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteDatabase("ExamResult.db");
                    // Reset SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("exam_prefs", MODE_PRIVATE);
                    prefs.edit().putInt("lastDeNumber", 0).apply(); // Đặt lại đề về Đề 1

                    // Xóa dữ liệu kết quả
                    SharedPreferences resultPrefs = getSharedPreferences("result_prefs", MODE_PRIVATE);
                    resultPrefs.edit().clear().apply();
                    //
                    Toast.makeText(this, "Đã xóa database và bắt đầu lại!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss(); //  hủy
                })
                .setCancelable(true) // cho bấm ngoài để tắt dialog
                .show();
    }
}