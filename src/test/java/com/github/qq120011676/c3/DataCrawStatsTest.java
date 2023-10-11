package com.github.qq120011676.c3;

import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.data.DataCraw;
import com.github.qq120011676.c3.data.impl.DataCrawStats;
import com.github.qq120011676.c3.entity.C3Area;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class DataCrawStatsTest {
    public static void main(String[] args) {
        DataCraw dataCraw = new DataCrawStats();
        List<C3Area> c3Areas = dataCraw.craw();
        String json = JSONUtil.toJsonStr(c3Areas);
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "data.json")
                .toAbsolutePath()
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("完成");
    }
}
