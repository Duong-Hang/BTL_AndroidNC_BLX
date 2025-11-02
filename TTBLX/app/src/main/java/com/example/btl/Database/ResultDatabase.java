package com.example.btl.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ResultDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="ExamResult.db";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="tbl_result";
    public static final String COLUMN_MKQ = "ma_ket_qua";
    public static final String COLUMN_MD = "ma_de";
    public static final String COLUMN_TongSoCau = "tong_so_cau";
    public static final String COLUMN_ThoiGianHoanThanh = "thoi_gian_hoan_thanh";
    public static final String COLUMN_NgayLam = "ngay_lam";
    public static final String COLUMN_SoCauDung = "so_cau_dung";
    public static final String COLUMN_KetQua = "ket_qua";

    // Câu lệnh tạo bảng
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_MKQ + " TEXT PRIMARY KEY, " +
                    COLUMN_MD + " TEXT, " +
                    COLUMN_TongSoCau + " INTEGER, " +
                    COLUMN_ThoiGianHoanThanh + " INTEGER, " +
                    COLUMN_NgayLam + " TEXT, " +
                    COLUMN_SoCauDung + " INTEGER, " +
                    COLUMN_KetQua + " TEXT" +
                    ")";

    public ResultDatabase( Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("DatabaseHelper", "Table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
