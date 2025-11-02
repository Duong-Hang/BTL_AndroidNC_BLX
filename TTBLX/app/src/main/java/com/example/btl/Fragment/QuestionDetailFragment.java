package com.example.btl.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl.Model.Question;
import com.example.btl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QuestionDetailFragment extends Fragment {
    TextView sttcauhoi, ndch, tvAnsw;
    ImageView imgQuestion;
    RadioGroup radio;
    RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    Button check;
    List<Question> ds;
    int index = 0;
    private Question q;
    private static final String ARG_QUESTION = "arg_question";
    private static final String ARG_INDEX = "arg_index";
    private static final String ARG_SHOW_CHECK = "arg_show_check";

    private boolean showCheckButton = true; // mặc định hiện nut check

    public void setShowCheckButton(boolean show) {//hien thi check
        this.showCheckButton = show;
    }

    //(hàm khởi tạo tùy chỉnh) dùng trong Fragment để:
    //Truyền dữ liệu an toàn cho Fragment khi nó được tạo ra.
    public static QuestionDetailFragment newInstance(Question q, int index, boolean showCheck) {
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, q);
        args.putInt(ARG_INDEX, index);
        args.putBoolean(ARG_SHOW_CHECK, showCheck);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            q = (Question) getArguments().getSerializable(ARG_QUESTION);
            showCheckButton = getArguments().getBoolean(ARG_SHOW_CHECK, true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detail_question, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ
        sttcauhoi = view.findViewById(R.id.tvQuestionIndex);
        ndch = view.findViewById(R.id.tvQuestionContent);
        imgQuestion = view.findViewById(R.id.imgQuestion);
        radio = view.findViewById(R.id.rgOptions);
        check = view.findViewById(R.id.btnSubmit);
        tvAnsw = view.findViewById(R.id.tvAnswer);
        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);
        rbOption4 = view.findViewById(R.id.rbOption4);
        //  LẤY DỮ LIỆU TỪ BUNDLE
        if (getArguments() != null) {
            q = (Question) getArguments().getSerializable(ARG_QUESTION);
            index = getArguments().getInt(ARG_INDEX, 0);
        }

        // HIỂN THỊ CÂU HỎI
        if (q != null) {
            hienthicauhoi();
        } else {
            tvAnsw.setText("Không có dữ liệu câu hỏi!");
        }

    }

    private void hienthicauhoi() {
        if(q.getTenNhomCauHoi().equals("Câu điểm liệt")){
            sttcauhoi.setText("Câu " + (index) + " *: ");
        }
        else{
            sttcauhoi.setText("Câu " + (index) + ": ");
        }
        ndch.setText(q.getNoiDungCauHoi());
        //xu ly hinh anh
        //raw   https://raw.githubusercontent.com/Duong-Hang/BTL_ANDROIDNC/refs/heads/main/TTBLX/anhquestion/c309.PNG
        String image = q.getHinhAnh();
        String baseUrl = "https://raw.githubusercontent.com/Duong-Hang/BTL_AndroidNC_BLX/refs/heads/main/TTBLX/anhquestion/";
        String imageUrl = baseUrl + image;
        if (image != null && !image.isEmpty()) {
            imgQuestion.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Ảnh tạm khi tải
                    .error(android.R.drawable.ic_delete) // Ảnh lỗi
                    .into(imgQuestion, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("Picasso", "Lỗi load ảnh: " + imageUrl, e);
                        }
                    });

            Log.d("Picasso", "URL câu " + index + ": " + imageUrl);

        } else {
            imgQuestion.setVisibility(View.GONE);
            imgQuestion.setImageDrawable(null);
        }
        //hien thi dap an de chon
        radio.removeAllViews();
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(options.get(i));
            radioButton.setId(View.generateViewId());
            radioButton.setTextSize(16);
            radioButton.setPadding(8, 8, 8, 8);
            radio.addView(radioButton); // Thêm vào RadioGroup
        }
        //luu dap an khi nguoi dung chon
        radio.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedButton = group.findViewById(checkedId);
            if (selectedButton != null) {
                q.setSelectedAnswer(selectedButton.getText().toString());
            }
        });
        //bam nut check
        if (!showCheckButton) {
            check.setVisibility(View.GONE);
            // Nếu câu hỏi đã có đáp án được chọn, hiển thị lại luôn
            if (q.getSelectedAnswer() != null) {
                for (int i = 0; i < radio.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) radio.getChildAt(i);
                    if (rb.getText().toString().equals(q.getSelectedAnswer())) {
                        rb.setChecked(true);
                        if (q.getSelectedAnswer().equals(q.getDapAnDung())) {
                            rb.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        } else {
                            rb.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            tvAnsw.setText("✔️ Đáp án đúng là: " + q.getDapAnDung() +
                                    "\n❓ Explain: " + q.getGiaiThichCauHoi());
                        }
                    } else {
                        // Những đáp án khác để màu đen
                        rb.setTextColor(getResources().getColor(android.R.color.black));
                    }
                }
            } else {
                // Nếu chưa có đáp án, để màu đen
                for (int i = 0; i < radio.getChildCount(); i++) {
                    ((RadioButton) radio.getChildAt(i))
                            .setTextColor(getResources().getColor(android.R.color.black));
                }
            }
        }
        else{
                //check dap an
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedId = radio.getCheckedRadioButtonId();
                        if (selectedId == -1) {
                            //chu mau den
                            for (int i = 0; i < radio.getChildCount(); i++) {
                                View v = radio.getChildAt(i);
                                if (v instanceof RadioButton) {
                                    ((RadioButton) v).setTextColor(getResources().getColor(android.R.color.black));
                                }
                            }
                            tvAnsw.setText("⚠️ Vui lòng chọn đáp án!");
                            return;
                        } else {
                            //chu tro lai mau den
                            for (int i = 0; i < radio.getChildCount(); i++) {
                                View v = radio.getChildAt(i);
                                if (v instanceof RadioButton) {
                                    ((RadioButton) v).setTextColor(getResources().getColor(android.R.color.black));
                                }
                                tvAnsw.setText("");
                            }
                            //------------------------------
                            RadioButton selectedButton = radio.findViewById(selectedId);
                            String answer = selectedButton.getText().toString();
                            // luu dap an duoc chon
                            q.setSelectedAnswer(answer);
                            if (answer.equals(q.getDapAnDung())) {
                                selectedButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            } else {
                                tvAnsw.setText("✔\uFE0F Đáp án đúng là:  " + q.getDapAnDung() + "\n" +
                                        "❓ Explain:" + q.getGiaiThichCauHoi());
                                selectedButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            }
                        }
                        // Nếu người dùng đã chọn đáp án trước đó, đánh dấu lại
                        if (q.getSelectedAnswer() != null) {
                            for (int i = 0; i < radio.getChildCount(); i++) {
                                RadioButton rb = (RadioButton) radio.getChildAt(i);
                                if (rb.getText().toString().equals(q.getSelectedAnswer())) {
                                    rb.setChecked(true);
                                    break;
                                }
                            }
                        }
                    }
                });
        }
    }
}
