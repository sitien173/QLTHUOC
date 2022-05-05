package com.ptithcm.qlthuoc;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DbContext extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QLTHUOC";
    private static final int DATABASE_VERSION = 1;
    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table1 = "CREATE TABLE \"AppUser\" (" +
                        "\"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "\"username\" TEXT NOT NULL UNIQUE, " +
                        "\"password\" TEXT NOT NULL, " +
                        "\"hoten\" TEXT NOT NULL, " +
                        "\"avatar\" BLOB, " +
                        "\"role\" TEXT NOT NULL, " +
                        "\"phone\" TEXT," +
                        "\"address\" TEXT); \n";
        String table2 = "CREATE TABLE \"Thuoc\" ( " +
                " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " \"tenthuoc\" TEXT NOT NULL, " +
                " \"thanhphan\" TEXT DEFAULT NULL, " +
                " \"donvitinh\" TEXT DEFAULT NULL, " +
                " \"soluong\" INTEGER DEFAULT NULL, " +
                " \"hinhanh\" BLOB, " +
                " \"dongia\" FLOAT DEFAULT NULL );";

        String table3 = "CREATE TABLE \"NhaThuoc\" ( " +
                " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " \"tennhathuoc\" TEXT NOT NULL, " +
                " \"diachi\" TEXT NOT NULL " +
                ");";

        String table4 = "CREATE TABLE \"HoaDon\" ( " +
                " \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " \"id_nhathuoc\" INTEGER DEFAULT NULL REFERENCES \"NhaThuoc\"(\"id\"), " +
                " \"khachhang\" TEXT DEFAULT NULL, " +
                " \"ghichu\" TEXT DEFAULT NULL, " +
                " \"total\" FLOAT DEFAULT NULL " +
                "); ";

        String table5 = "CREATE TABLE \"Thuoc_NhaThuoc\" ( " +
                " \"id_thuoc\" INTEGER DEFAULT NULL REFERENCES \"Thuoc\"(\"id\"), " +
                " \"id_nhathuoc\" INTEGER DEFAULT NULL REFERENCES \"NhaThuoc\"(\"id\")" +
                ");";
        String table6 = "CREATE TABLE \"CT_BanLe\" ( " +
                " \"id_thuoc\" INTEGER DEFAULT NULL REFERENCES \"Thuoc\"(\"id\"), " +
                " \"id_hoadon\" INTEGER DEFAULT NULL REFERENCES \"HoaDon\"(\"id\"), " +
                " \"id_customer\" INTEGER DEFAULT NULL REFERENCES \"AppUser\"(\"id\"), " +
                " \"soluong\" INTEGER DEFAULT NULL, " +
                " \"status\" INTEGER DEFAULT NULL, " +
                " \"total\" FLOAT DEFAULT NULL" +
                ");";

        // create account admin
        String insertAdmin = "INSERT INTO AppUser(username,password,hoten,role) VALUES('adm','adm','ADMIN', 'ADMIN');";


        sqLiteDatabase.execSQL(table1);
        sqLiteDatabase.execSQL(table2);
        sqLiteDatabase.execSQL(table3);
        sqLiteDatabase.execSQL(table4);
        sqLiteDatabase.execSQL(table5);
        sqLiteDatabase.execSQL(table6);
        sqLiteDatabase.execSQL(insertAdmin);
        Log.v("create database---", "Successfully");
    }
}
