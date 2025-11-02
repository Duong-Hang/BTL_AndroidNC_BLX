package com.example.btl.Model;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String maCauHoi;
    private String tenNhomCauHoi;
    private String maDe;
    private String thuTu;
    private String noiDungCauHoi;
    private List<String> options;
    private String dapAnDung;
    private String hinhAnh;
    private String giaiThichCauHoi;
    private String selectedAnswer;

    public Question() {
    }

    public Question(String maCauHoi, String noiDungCauHoi, List<String> options, String dapAnDung, String hinhAnh, String giaiThichCauHoi, String selectedAnswer) {
        this.maCauHoi = maCauHoi;
        this.tenNhomCauHoi = tenNhomCauHoi;
        this.noiDungCauHoi = noiDungCauHoi;
        this.options = options;
        this.dapAnDung = dapAnDung;
        this.hinhAnh = hinhAnh;
        this.giaiThichCauHoi = giaiThichCauHoi;
        this.selectedAnswer = selectedAnswer;
    }

    @PropertyName("question_number")
    public String getMaCauHoi() {
        return maCauHoi;
    }

    @PropertyName("question_number")
    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    @PropertyName("category")
    public String getTenNhomCauHoi() {
        return tenNhomCauHoi;
    }

    @PropertyName("category")
    public void setTenNhomCauHoi(String tenNhomCauHoi) {
        this.tenNhomCauHoi = tenNhomCauHoi;
    }

    @PropertyName("question")
    public String getNoiDungCauHoi() {
        return noiDungCauHoi;
    }
    @PropertyName("question")
    public void setNoiDungCauHoi(String noiDungCauHoi) {
        this.noiDungCauHoi = noiDungCauHoi;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @PropertyName("correct_answer")
    public String getDapAnDung() {
        return dapAnDung;
    }

    @PropertyName("correct_answer")
    public void setDapAnDung(String dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    @PropertyName("image")
    public String getHinhAnh() {
        return hinhAnh;
    }

    @PropertyName("image")
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @PropertyName("explanation")
    public String getGiaiThichCauHoi() {
        return giaiThichCauHoi;
    }

    @PropertyName("explanation")
    public void setGiaiThichCauHoi(String giaiThichCauHoi) {
        this.giaiThichCauHoi = giaiThichCauHoi;
    }

}
