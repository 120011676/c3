package com.github.qq120011676.c3;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.entity.C3Area;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataProvinceCityCountyStreetCommunityTest {
    public static void main(String[] args) {
        DataProvinceCityCountyStreetCommunityTest d = new DataProvinceCityCountyStreetCommunityTest();
        List<C3Area> pccList = d.read("china_province_city_county.json");
        List<C3Area> streetList = d.read("street.json");
        List<C3Area> communityList = d.read("community.json");
        Map<String, List<C3Area>> streetMap = streetList.stream()
                .collect(Collectors
                        .groupingBy(o -> o.getCode()
                                .substring(0, 6) + "000000"));
        Map<String, List<C3Area>> communityMap = communityList.stream()
                .collect(Collectors.groupingBy(
                        (o) -> o.getCode()
                                .substring(0, 9) + "000"));
        streetList.forEach(o -> o.setChilds(communityMap.get(o.getCode())));
        Map<String, C3Area> pccMap = new HashMap<>(200000);
        for (C3Area p : pccList) {
            if (p.getChilds() == null) {
                continue;
            }
            for (C3Area c : p.getChilds()) {
                if (c.getChilds() == null) {
                    continue;
                }
                for (C3Area co : c.getChilds()) {
                    pccMap.put(co.getCode(), co);
                }
            }
        }
        streetMap.forEach((k, v) -> pccMap.get(k).setChilds(v));
        String json = JSONUtil.toJsonStr(pccList);
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "china_province_city_county_street_community.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
