package com.example.btl.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.btl.Model.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDao {
    private  ResultDatabase db;
    private SQLiteDatabase database;
    public ResultDao(Context context){
        db = new ResultDatabase(context);
        database = db.getWritableDatabase();
    }
    public void insert(Context context,String ma_ket_qua,String ma_de,int tong_so_cau,int thoi_gian_hoan_thanh,String ngay_lam,int soCauDung,String ketQua){
        ContentValues values = new ContentValues();
        values.put(db.COLUMN_MKQ,ma_ket_qua);
        values.put(db.COLUMN_MD,ma_de);
        values.put((db.COLUMN_TongSoCau),tong_so_cau);
        values.put((db.COLUMN_ThoiGianHoanThanh),thoi_gian_hoan_thanh);
        values.put(db.COLUMN_NgayLam,ngay_lam);
        values.put((db.COLUMN_SoCauDung),soCauDung);
        values.put(db.COLUMN_KetQua,ketQua);

        long kq=database.insert(db.TABLE_NAME,null,values);
        if(kq==-1){
            Log.e("ExamDetail", "Lỗi khi lưu kết quả: " );
        }
        else{
            Log.e("ExamDetail", "Insert kết quả success.");
        }
    }
    public List<Result> getAllResults(String maDe) {
        List<Result> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ResultDatabase.TABLE_NAME + " WHERE " +
                ResultDatabase.COLUMN_MD + " = ? ORDER BY " + ResultDatabase.COLUMN_NgayLam + " DESC", new String[]{maDe});
        if (cursor.moveToFirst()) {
            do {
                Result result = new Result(
                        cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_MKQ)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_MD)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_TongSoCau)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_ThoiGianHoanThanh)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_NgayLam)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_SoCauDung)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_KetQua))
                );
                list.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }
    public List<Result> getsds(){
        List<Result> list = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT ma_de,tong_so_cau,thoi_gian_hoan_thanh,ngay_lam,so_cau_dung,ket_qua FROM tbl_result", null);
            if (cursor.moveToFirst()) {
                do {
                    Result result = new Result(
                            null,
                            cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_MD)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_TongSoCau)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_ThoiGianHoanThanh)),
                            cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_NgayLam)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_SoCauDung)),
                            cursor.getString(cursor.getColumnIndexOrThrow(ResultDatabase.COLUMN_KetQua))
                    );
                    list.add(result);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            Log.e("ExamDetail", "Lỗi khi lấy danh sách kết quả: " + e.getMessage());
        }
        return list;
    }

    public long delete() {
        return database.delete(ResultDatabase.TABLE_NAME, null, null);
    }
}
