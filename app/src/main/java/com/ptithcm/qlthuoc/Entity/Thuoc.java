package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.Collection;


public class Thuoc implements Serializable {
    private int id;
    private String tenthuoc;
    private String thanhphan;
    private String donvitinh;
    private int soluong;
    private byte[] hinhanh;
    private float dongia;
    private Collection<Thuoc_NhaThuoc> nhaThuocs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenthuoc() {
        return tenthuoc;
    }

    public void setTenthuoc(String tenthuoc) {
        this.tenthuoc = tenthuoc;
    }

    public String getThanhphan() {
        return thanhphan;
    }

    public void setThanhphan(String thanhphan) {
        this.thanhphan = thanhphan;
    }

    public String getDonvitinh() {
        return donvitinh;
    }

    public void setDonvitinh(String donvitinh) {
        this.donvitinh = donvitinh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public Collection<Thuoc_NhaThuoc> getNhaThuocs() {
        return nhaThuocs;
    }

    public void setNhaThuocs(Collection<Thuoc_NhaThuoc> nhaThuocs) {
        this.nhaThuocs = nhaThuocs;
    }
}
