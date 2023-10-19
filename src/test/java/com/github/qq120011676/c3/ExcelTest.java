package com.github.qq120011676.c3;

import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.qq120011676.c3.entity.ChinaCityCode;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelTest {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "2023年度全国统计用区划代码和城乡划分代码_15位（发布版）.xlsx")
                .toString();
        List<ChinaCityCode> c3s = new ArrayList<>();
        ExcelUtil.readBySax(filepath, 0,
                (sheetIndex, rowIndex, rowlist) -> {
                    if (rowIndex > 0) {
                        String code = Optional.ofNullable(rowlist)
                                .map(v -> v.get(6))
                                .map(v -> (String) v)
                                .map(String::trim)
                                .orElse(null);
                        String name = Optional.ofNullable(rowlist)
                                .map(v -> v.get(7))
                                .map(v -> (String) v)
                                .map(String::trim)
                                .orElse(null);
                        ChinaCityCode c3 = new ChinaCityCode();
                        c3.setCode(code);
                        c3.setName(name);
                        c3s.add(c3);
                    }
//                    if (rowIndex < 10) {
//                        Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
//                    }
                });
        Map<String, ChinaCityCode> provinceMap = new HashMap<>();
        Map<String, ChinaCityCode> cityMap = new HashMap<>();
        Map<String, ChinaCityCode> countyMap = new HashMap<>();
        Map<String, ChinaCityCode> countryMap = new HashMap<>();
        Map<String, ChinaCityCode> villageMap = new HashMap<>();
        for (ChinaCityCode o : c3s) {
            if (o.getCode().startsWith("0000000000", 2)) {
                provinceMap.put(o.getCode(), o);
                continue;
            }
            if (o.getCode().startsWith("00000000", 4)) {
                cityMap.put(o.getCode(), o);
                continue;
            }
            if (o.getCode().startsWith("000000", 6)) {
                countyMap.put(o.getCode(), o);
                continue;
            }
            if (o.getCode().startsWith("000", 9)) {
                countryMap.put(o.getCode(), o);
                continue;
            }
            villageMap.put(o.getCode(), o);
        }
        c3s.forEach(o -> {
            if (o.getCode().endsWith("0000000000000")) {
                provinceMap.put(o.getCode(), o);
            }
        });
        System.out.println(provinceMap.size());
        System.out.println(cityMap.size());
        System.out.println(countyMap.size());
        System.out.println(countryMap.size());
        System.out.println(villageMap.size());
        provinceMap.forEach((k, v) -> {
            if (!v.getCode().endsWith("000")) {
                System.out.println(v);
            }
        });
        cityMap.forEach((k, v) -> {
            if (!v.getCode().endsWith("000")) {
                System.out.println(v);
            }
        });
        countyMap.forEach((k, v) -> {
            if (!v.getCode().endsWith("000")) {
                System.out.println(v);
            }
        });
        countryMap.forEach((k, v) -> {
            if (!v.getCode().endsWith("000")) {
                System.out.println(v);
            }
        });
        provinceMap.forEach((k, v) -> v.setChilds(new ArrayList<>()));
        cityMap.forEach((k, v) -> {
            v.setChilds(new ArrayList<>());
            provinceMap.get(v.getCode()
                            .replaceAll("(\\d{2})\\d{2}(\\d{11})",
                                    "$100$2"))
                    .getChilds()
                    .add(v);
        });
        countyMap.forEach((k, v) -> {
            v.setChilds(new ArrayList<>());
            cityMap.get(v.getCode()
                            .replaceAll("(\\d{4})\\d{2}(\\d{9})",
                                    "$100$2"))
                    .getChilds()
                    .add(v);
        });
        countryMap.forEach((k, v) -> {
            String p_code = v.getCode()
                    .replaceAll("(\\d{6})\\d{3}(\\d{6})",
                            "$1000$2");
            ChinaCityCode c3 = Optional.ofNullable(countyMap.get(p_code))
                    .orElse(cityMap.get(p_code));
            v.setChilds(new ArrayList<>());
            c3.getChilds()
                    .add(v);
        });
        villageMap.forEach((k, v) -> {
            countryMap.get(v.getCode()
                            .replaceAll("(\\d{9})\\d{3}(\\d{3})",
                                    "$1000000"))
                    .getChilds()
                    .add(v);
        });
        List<ChinaCityCode> c3List = sort(provinceMap.values());
        String json = JSONUtil.toJsonStr(c3List);
        filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "china_province_city_county_street_community_15.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try (ExcelReader reader = ExcelUtil.getReader(filepath)) {
//            int rowCount = reader.getRowCount();
//            int tmp = rowCount / 10;
//            List<java.util.List<Object>> list = reader.read(0, tmp);
//            System.out.println(list.size());
//            System.out.println(list.get(1));
//        }
    }

    public static List<ChinaCityCode> sort(Collection<ChinaCityCode> c3s) {
        if (Objects.isNull(c3s) || c3s.isEmpty()) {
            return null;
        }
        return c3s.stream()
                .sorted(Comparator.comparing(ChinaCityCode::getCode))
                .peek(o -> o.setChilds(sort(o.getChilds())))
                .collect(Collectors.toList());
    }
}
