package com.github.qq120011676.c3;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.entity.C3Area;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataProvinceCityCountyTest {
    public static void main(String[] args) {
        DataProvinceCityCountyTest d = new DataProvinceCityCountyTest();
        List<C3Area> provinceList = d.read("province.json");
        List<C3Area> cityList = d.read("city.json");
        List<C3Area> countyList = d.read("county.json");
        Map<String, List<C3Area>> cityMap = cityList.stream()
                .collect(Collectors
                        .groupingBy(o -> o.getCode()
                                .substring(0, 2) + "0000000000"));
        Map<String, List<C3Area>> countyMap = countyList.stream()
                .collect(Collectors.groupingBy(
                        (o) -> o.getCode()
                                .substring(0, 4) + "00000000"));
        cityList.forEach(o -> o.setChilds(countyMap.get(o.getCode())));
        provinceList.forEach(o -> o.setChilds(cityMap.get(o.getCode())));
        String json = JSONUtil.toJsonStr(provinceList);
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "china_province_city_county.json")
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
