package com.github.qq120011676.c3;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.entity.ChinaCityCode;

import java.nio.file.Paths;
import java.util.List;

public class D15V {

    public static void main(String[] args) {
        D15V vt = new D15V();
        List<ChinaCityCode> list = vt.read("china_province_city_county_street_community_15.json");
        System.out.println(list
                .get(0)
                .getChilds()
                .get(0)
                .getChilds()
                .get(0)
                .getChilds()
                .get(0)
                .getChilds()
                .get(0));
    }

    public List<ChinaCityCode> read(String filename) {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        filename)
                .toString();
        FileReader fileReader = new FileReader(filepath);
        String json = fileReader.readString();
        return JSONUtil.toBean(json,
                new TypeReference<>() {
                },
                false);
    }
}
