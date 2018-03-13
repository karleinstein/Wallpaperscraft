package com.example.karleinstein.wallpaper;

import java.io.Serializable;

public class HinhAnh {
    public String tenAnh;
    public String hinhAnhz;

    public HinhAnh(String tenAnh, String hinhAnhz) {
        this.tenAnh = tenAnh;
        this.hinhAnhz = hinhAnhz;
    }


    public String getTenAnh() {
        return tenAnh;
    }

    public String getHinhAnhz() {
        return hinhAnhz;
    }
}
