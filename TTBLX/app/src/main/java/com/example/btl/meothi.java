package com.example.btl;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl.Fragment.MenuFragment;

public class meothi extends AppCompatActivity {
    TextView txtmeothi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meothi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtmeothi=findViewById(R.id.txtmeothi);
        //them menu
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        //nd
        String fullText =
                "Mẹo thi lý thuyết bằng lái xe A1" +
                        "\n" +
                        "\n" +
                        "Phần 1. Lý thuyết" +
                        "\n" +
                        "- Những ý đúng: đáp án có những từ này => chọn luôn, không cần chú ý câu hỏi:" +
                        "\n" +
                        "\t\t+ Bị nghiêm cấm" +
                        "\n" +
                        "\t\t+ Không được… phép làm, vượt, mang vác" +
                        "\n" +
                        "\t\t+ 5m, 50 năm, 18 tuổi" +
                        "\n" +
                        "\t\t+ Tất cả các ý trên" +
                        "\n" +
                        "\n" +
                        "- Tốc độ: " +
                        "\n" +
                        "\t\t+ Xe gắn máy: 40km/h" +
                        "\n" +
                        "\t\t+ Xe máy, ô tô không có giải phân cách: 50km/h" +
                        "\n" +
                        "\t\t+ Xe máy, ô tô có giải phân cách: 60km/h" +
                        "\n" +
                        "\n" +
                        "- Những đáp án “cả ý 1 và 2” => thường là đáp án đúng, còn lại chọn đáp án 1" +
                        "\n" +
                        "\n" +
                        "- Câu hỏi “đỗ xe”, “dừng xe” => chọn đáp án 2"+
                        "\n" +
                        "\n" +
                        "- Câu hỏi “giải phân cách”:" +
                        "\n" +
                        "\t\t+ Dài 1 dòng => chọn đáp án 1" +
                        "\n" +
                        "\t\t+ Dài 2 dòng => chọn đáp án 2" +
                        "\n" +
                        "\n" +
                        "Phần 2. Biển báo" +
                        "\n" +
                        "-\t“Tốc độ tối đa” => chọn số nhỏ (50).\n-\t“Tốc độ tối thiểu” => chọn số lớn (60).\n" +
                        "-\tVạch vàng là ngược chiều (2 chiều)\n" +
                        "-\tVạch trắng là cùng chiều (1 chiều)\n" +
                        "-\tVạch nét liền không được đè vạch.\n" +
                        "-\tVạch nét đứt được đè vạch.\n" +
                        "\n" +
                        "Phần 3. Sa hình" +
                        "\n" +
                        "-\tThứ tự: xe ưu tiên – đường ưu tiên – bên phải trống – rẽ phải – đi thẳng – rẽ trái.\n" +
                        "-\tCâu liên quan đến vòng xuyến và CSGT => chọn đáp án 3.\n" +
                        "-\tCâu hỏi về xe vi phạm => chọn đáp án không có xe con vì xe con luôn đúng.\n";

        //In dam
        String[] linesToBold = {
                "Phần 1. Lý thuyết",
                "Phần 2. Biển báo",
                "Phần 3. Sa hình"
        };

        // In đậm từng dòng trong danh sách
        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);
        for (String line : linesToBold) {
            int start = fullText.indexOf(line);
            if (start != -1) {
                int end = start + line.length();
                builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        txtmeothi.setText(builder);

    }
}