package com.ptithcm.qlthuoc.Entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Thuoc_NhaThuoc implements Serializable {
    private Thuoc thuoc;
    private NhaThuoc nhaThuoc;
}
