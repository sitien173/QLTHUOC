package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class NhaThuoc implements Serializable {
    private int id;
    private String tennhathuoc;
    private String diachi;
    private Collection<Thuoc_NhaThuoc> thuocs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTennhathuoc() {
        return tennhathuoc;
    }

    public void setTennhathuoc(String tennhathuoc) {
        this.tennhathuoc = tennhathuoc;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Collection<Thuoc_NhaThuoc> getThuocs() {
        return thuocs;
    }

    public void setThuocs(Collection<Thuoc_NhaThuoc> thuocs) {
        this.thuocs = thuocs;
    }
}
