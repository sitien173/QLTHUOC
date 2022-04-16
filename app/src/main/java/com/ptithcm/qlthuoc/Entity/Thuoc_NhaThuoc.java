package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;

public class Thuoc_NhaThuoc implements Serializable {
    private Thuoc thuoc;
    private NhaThuoc nhaThuoc;

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public NhaThuoc getNhaThuoc() {
        return nhaThuoc;
    }

    public void setNhaThuoc(NhaThuoc nhaThuoc) {
        this.nhaThuoc = nhaThuoc;
    }
}
