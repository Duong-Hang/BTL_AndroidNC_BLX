package com.example.btl.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.btl.ExeamDetail;
import com.example.btl.MainActivity;
import com.example.btl.Nghidinh;
import com.example.btl.R;
import com.example.btl.Setting;
import com.example.btl.lichsu;
import com.example.btl.meothi;
import com.example.btl.thithu;

public class MenuFragment extends Fragment {
    private LinearLayout layoutQuestion, layoutExam, layoutHistory, layoutTips, layoutDecree,layoutsetting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chung, container, false);

        // Ánh xạ các LinearLayout từ layout
        layoutQuestion = view.findViewById(R.id.layoutQuestion);
        layoutExam = view.findViewById(R.id.layoutExam);
        layoutHistory = view.findViewById(R.id.layoutHistory);
        layoutTips = view.findViewById(R.id.layoutTips);
        layoutDecree = view.findViewById(R.id.layoutDecree);
        layoutsetting=view.findViewById(R.id.layoutsetting);

        // Thiết lập sự kiện click chung cho các mục menu
        View.OnClickListener menuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                // kiểm tra ID của view
                if (v.getId() == R.id.layoutQuestion) {
                    intent = new Intent(getActivity(), MainActivity.class);
                } else if (v.getId() == R.id.layoutExam) {
                    intent = new Intent(getActivity(), thithu.class);
                } else if (v.getId() == R.id.layoutHistory) {
                    intent = new Intent(getActivity(), lichsu.class);
                } else if (v.getId() == R.id.layoutTips) {
                    intent = new Intent(getActivity(), meothi.class);
                } else if (v.getId() == R.id.layoutDecree) {
                    intent = new Intent(getActivity(), Nghidinh.class);
                } else if (v.getId() == R.id.layoutsetting) {
                    intent= new Intent(getActivity(), Setting.class);
                }

                if (intent != null) {
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "Không có Intent phù hợp", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Gán OnClickListener cho tất cả các mục menu
        layoutQuestion.setOnClickListener(menuClickListener);
        layoutExam.setOnClickListener(menuClickListener);
        layoutHistory.setOnClickListener(menuClickListener);
        layoutTips.setOnClickListener(menuClickListener);
        layoutDecree.setOnClickListener(menuClickListener);
        layoutsetting.setOnClickListener(menuClickListener);

        return view;
    }
}
