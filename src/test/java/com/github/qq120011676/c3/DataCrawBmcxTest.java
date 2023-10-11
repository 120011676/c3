package com.github.qq120011676.c3;

import com.github.qq120011676.c3.data.DataCraw;
import com.github.qq120011676.c3.data.impl.DataCrawBmcx;
import com.github.qq120011676.c3.entity.C3Area;

import java.util.List;

public class DataCrawBmcxTest {
    public static void main(String[] args) {
        DataCraw dataCraw = new DataCrawBmcx();
        List<C3Area> c3Areas = dataCraw.craw();
        System.out.println(c3Areas);
    }
}
