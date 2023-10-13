package com.github.qq120011676.c3;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.github.qq120011676.c3.entity.C3AreaExt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataCrawStatsNew {
    public static void main(String[] args) {
        DataCrawStatsNew dataCrawStatsNew = new DataCrawStatsNew();
        dataCrawStatsNew.community();
        System.out.println("完成");
    }

    public void p() {
        DataCrawStatsNew dataCrawStatsNew = new DataCrawStatsNew();
        List<C3AreaExt> c3Areas = dataCrawStatsNew.provincetr("http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/index.html");
        String json = JSONUtil.toJsonStr(c3Areas);
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "province.json")
                .toAbsolutePath()
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void c() {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "province.json")
                .toString();
        FileReader fileReader = new FileReader(filepath);
        String json = fileReader.readString();
        List<C3AreaExt> exts = JSONUtil.toBean(json,
                new TypeReference<List<C3AreaExt>>() {
                },
                false);
        List<C3AreaExt> list = new ArrayList<>(100000);
        DataCrawStatsNew dcsn = new DataCrawStatsNew();
        exts.forEach(o -> {
            list.addAll(dcsn.citytr(o.getUrl()));
        });
        json = JSONUtil.toJsonStr(list);
        filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "city.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void x() {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "city.json")
                .toString();
        FileReader fileReader = new FileReader(filepath);
        String json = fileReader.readString();
        List<C3AreaExt> exts = JSONUtil.toBean(json,
                new TypeReference<List<C3AreaExt>>() {
                },
                false);
        List<C3AreaExt> list = new ArrayList<>(100000);
        DataCrawStatsNew dcsn = new DataCrawStatsNew();
        exts.forEach(o -> {
            if (Objects.nonNull(o.getUrl()) && !o.getUrl().isBlank()) {
                list.addAll(dcsn.countytr(o.getUrl()));
            }
        });
        json = JSONUtil.toJsonStr(list);
        filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "county.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void street() {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "county.json")
                .toString();
        FileReader fileReader = new FileReader(filepath);
        String json = fileReader.readString();
        List<C3AreaExt> exts = JSONUtil.toBean(json,
                new TypeReference<>() {
                },
                false);
        List<C3AreaExt> list = new ArrayList<>(200000);
        DataCrawStatsNew dcsn = new DataCrawStatsNew();
        exts.forEach(o -> {
            if (Objects.nonNull(o.getUrl()) && !o.getUrl().isBlank()) {
                list.addAll(dcsn.towntr(o.getUrl()));
            }
        });
        json = JSONUtil.toJsonStr(list);
        filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "street.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void community() {
        String projectPath = System.getProperty("user.dir");
        String filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "street.json")
                .toString();
        FileReader fileReader = new FileReader(filepath);
        String json = fileReader.readString();
        List<C3AreaExt> exts = JSONUtil.toBean(json,
                new TypeReference<>() {
                },
                false);
        List<C3AreaExt> list = new ArrayList<>(500000);
        DataCrawStatsNew dcsn = new DataCrawStatsNew();
        exts.forEach(o -> {
            if (Objects.nonNull(o.getUrl()) && !o.getUrl().isBlank()) {
                list.addAll(dcsn.villagetr(o.getUrl()));
            }
        });
        json = JSONUtil.toJsonStr(list);
        filepath = Paths.get(projectPath,
                        "src",
                        "test",
                        "resources",
                        "community.json")
                .toString();
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private final String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36";
    private final int sleep = 500;
    private final int timeout = 30000;


    public List<C3AreaExt> parse() {
        return provincetr("http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/index.html");
    }

    public List<C3AreaExt> provincetr(String url) {
        try {
            Thread.sleep(sleep);
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .timeout(timeout)
                    .get()
                    .select(".provincetr a")
                    .stream()
                    .map(o -> {
                        String name = o.text();
                        String href = o.attr("href");
                        String code = href.substring(0, href.indexOf("."));
                        String absUrl = o.absUrl("href");
                        C3AreaExt c3Area = new C3AreaExt();
                        c3Area.setCode(MessageFormat.format("{0}0000000000", code));
                        c3Area.setName(name);
                        c3Area.setUrl(absUrl);
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3AreaExt> citytr(String url) {
        try {
            Thread.sleep(sleep);
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .timeout(timeout)
                    .get()
                    .select(".citytr")
                    .stream()
                    .map(o -> {
                        Elements elements = o.children();
                        Element codeElement = elements.get(0);
                        Element nameElement = elements.get(1);
                        String code = Optional.of(codeElement)
                                .map(Element::text)
                                .orElse(null);
                        String name = Optional.of(nameElement)
                                .map(Element::text)
                                .orElse(null);
                        C3AreaExt c3Area = new C3AreaExt();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(c3Area::setUrl);
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3AreaExt> countytr(String url) {
        try {
            Thread.sleep(sleep);
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .timeout(timeout)
                    .get()
                    .select(".countytr")
                    .stream()
                    .map(o -> {
                        Elements elements = o.children();
                        Element codeElement = elements.get(0);
                        Element nameElement = elements.get(1);
                        String code = Optional.of(codeElement)
                                .map(Element::text)
                                .orElse(null);
                        String name = Optional.of(nameElement)
                                .map(Element::text)
                                .orElse(null);
                        C3AreaExt c3Area = new C3AreaExt();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(c3Area::setUrl);
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3AreaExt> towntr(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .timeout(timeout)
                    .get()
                    .select(".towntr")
                    .stream()
                    .map(o -> {
                        Elements elements = o.children();
                        Element codeElement = elements.get(0);
                        Element nameElement = elements.get(1);
                        String code = Optional.of(codeElement)
                                .map(Element::text)
                                .orElse(null);
                        String name = Optional.of(nameElement)
                                .map(Element::text)
                                .orElse(null);
                        C3AreaExt c3Area = new C3AreaExt();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(c3Area::setUrl);
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }


    public List<C3AreaExt> villagetr(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .timeout(timeout)
                    .get()
                    .select(".villagetr")
                    .stream()
                    .map(o -> {
                        Elements elements = o.children();
                        Element codeElement = elements.get(0);
                        Element typeElement = elements.get(1);
                        Element nameElement = elements.get(2);
                        String code = Optional.of(codeElement)
                                .map(Element::text)
                                .orElse(null);
                        String type = Optional.of(typeElement)
                                .map(Element::text)
                                .orElse(null);
                        String name = Optional.of(nameElement)
                                .map(Element::text)
                                .orElse(null);
                        C3AreaExt c3Area = new C3AreaExt();
                        c3Area.setCode(code);
                        c3Area.setType(type);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(System.out::println);
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }
}
