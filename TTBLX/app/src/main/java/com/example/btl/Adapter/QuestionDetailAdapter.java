package com.example.btl.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.btl.Fragment.QuestionDetailFragment;
import com.example.btl.Model.Question;

import java.util.List;

public class QuestionDetailAdapter extends FragmentStateAdapter {
    private List<Question> questionList;
    private boolean showCheckButton;

    public QuestionDetailAdapter(@NonNull FragmentActivity activity, List<Question> questionList,boolean showCheckButton) {
        super(activity);   // CHỈ cần truyền FragmentActivity
        this.questionList = questionList;
        this.showCheckButton = showCheckButton;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (questionList == null || questionList.isEmpty()) {
            // Trả về 1 fragment rỗng để tránh crash
            return new Fragment();
        }
        Question question = questionList.get(position);
        return QuestionDetailFragment.newInstance(question, position + 1, showCheckButton);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
    //  Cho phép cập nhật lại dữ liệu khi có danh sách mới (ví dụ từ Firestore)
    public void updateData(List<Question> newList) {
        this.questionList = newList;
        notifyDataSetChanged();
    }
}
