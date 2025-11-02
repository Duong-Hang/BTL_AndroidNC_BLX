package com.example.btl.Model;

import java.util.Map;

public class Result {
    private String ma_ket_qua;
    private String ma_de;
    private int tong_so_cau;
    private int thoi_gian_hoan_thanh;
    private String ngay_lam;
    public int soCauDung;
    public String ketQua;

    public Result(String ma_ket_qua, String ma_de, int tong_so_cau, int thoi_gian_hoan_thanh, String ngay_lam, int soCauDung, String ketQua) {
        this.ma_ket_qua = ma_ket_qua;
        this.ma_de = ma_de;
        this.tong_so_cau = tong_so_cau;
        this.thoi_gian_hoan_thanh = thoi_gian_hoan_thanh;
        this.ngay_lam = ngay_lam;
        this.soCauDung = soCauDung;
        this.ketQua = ketQua;
    }

    public String getMa_ket_qua() {
        return ma_ket_qua;
    }

    public void setMa_ket_qua(String ma_ket_qua) {
        this.ma_ket_qua = ma_ket_qua;
    }

    public String getMa_de() {
        return ma_de;
    }

    public void setMa_de(String ma_de) {
        this.ma_de = ma_de;
    }
    public int getTong_so_cau() {
        return tong_so_cau;
    }

    public void setTong_so_cau(int tong_so_cau) {
        this.tong_so_cau = tong_so_cau;
    }

    public int getThoi_gian_hoan_thanh() {
        return thoi_gian_hoan_thanh;
    }

    public void setThoi_gian_hoan_thanh(int thoi_gian_hoan_thanh) {
        this.thoi_gian_hoan_thanh = thoi_gian_hoan_thanh;
    }

    public String getNgay_lam() {
        return ngay_lam;
    }

    public void setNgay_lam(String ngay_lam) {
        this.ngay_lam = ngay_lam;
    }

    public int getSoCauDung() {
        return soCauDung;
    }

    public void setSoCauDung(int soCauDung) {
        this.soCauDung = soCauDung;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }
}
