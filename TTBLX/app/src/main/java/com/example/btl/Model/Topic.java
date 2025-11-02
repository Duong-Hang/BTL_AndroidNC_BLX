package com.example.btl.Model;

public class Topic {

    private String ten_nhom_cau_hoi;
    private int so_cau_hoi;
    private String hinh_anh;

    public Topic() {
        // Bắt buộc cho Firestore
    }
    public Topic( String ten_nhom_cau_hoi, int so_cau_hoi, String hinh_anh) {
        this.ten_nhom_cau_hoi = ten_nhom_cau_hoi;
        this.so_cau_hoi = so_cau_hoi;
        this.hinh_anh = hinh_anh;
    }



    public String getTen_nhom_cau_hoi() {
        return ten_nhom_cau_hoi;
    }

    public void setTen_nhom_cau_hoi(String ten_nhom_cau_hoi) {
        this.ten_nhom_cau_hoi = ten_nhom_cau_hoi;
    }

    public int getSo_cau_hoi() {
        return so_cau_hoi;
    }

    public void setSo_cau_hoi(int so_cau_hoi) {
        this.so_cau_hoi = so_cau_hoi;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
}
