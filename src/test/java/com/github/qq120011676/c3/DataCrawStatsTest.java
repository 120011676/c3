package com.github.qq120011676.c3;

import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.data.DataCraw;
import com.github.qq120011676.c3.data.impl.DataCrawStats;
import com.github.qq120011676.c3.entity.C3Area;

import java.util.List;

public class DataCrawStatsTest {
    public static void main(String[] args) {
        DataCraw dataCraw = new DataCrawStats();
        List<C3Area> c3Areas = dataCraw.craw();
        System.out.println(JSONUtil.toJsonStr(c3Areas));
    }
}
