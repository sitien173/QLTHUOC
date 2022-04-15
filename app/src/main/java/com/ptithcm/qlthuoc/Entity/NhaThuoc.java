package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NhaThuoc implements Serializable {
    private int id;
    private String tennhathuoc;
    private String diachi;
    private Collection<Thuoc_NhaThuoc> thuocs = new ArrayList<>();
}
