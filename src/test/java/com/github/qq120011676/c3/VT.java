package com.github.qq120011676.c3;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.entity.C3Area;

import java.nio.file.Paths;
import java.util.List;

public class VT {
    public static void main(String[] args) {
        VT vt = new VT();
        List<C3Area> list = vt.read("china_province_city_county_street_community.json");
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

    public List<C3Area> read(String filename) {
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
