package com.github.qq120011676.c3.data.impl;

import com.github.qq120011676.c3.data.DataCraw;
import com.github.qq120011676.c3.entity.C3Area;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataCrawStats implements DataCraw {
    private final String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36";
    private final int sleep = 3000;
    private final int timeout = 30000;

    @Override
    public List<C3Area> craw() {
        return parse();
    }

    public List<C3Area> parse() {
        return provincetr("http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/index.html");
    }

    public List<C3Area> provincetr(String url) {
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
                        C3Area c3Area = new C3Area();
                        c3Area.setCode(MessageFormat.format("{0}0000000000", code));
                        c3Area.setName(name);
                        c3Area.setChilds(citytr(absUrl));
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3Area> citytr(String url) {
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
                        C3Area c3Area = new C3Area();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(v -> c3Area.setChilds(countytr(v)));
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3Area> countytr(String url) {
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
                        C3Area c3Area = new C3Area();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(v -> c3Area.setChilds(towntr(v)));
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }

    public List<C3Area> towntr(String url) {
        try {
            Thread.sleep(sleep);
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
                        C3Area c3Area = new C3Area();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        Elements aElements = codeElement.getElementsByTag("a");
                        Optional.of(aElements)
                                .filter(v -> !v.isEmpty())
                                .map(v -> v.get(0))
                                .map(v -> v.absUrl("href"))
                                .filter(v -> !v.isBlank())
                                .ifPresent(v -> c3Area.setChilds(villagetr(v)));
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(url);
            throw new RuntimeException(e);
        }
    }


    public List<C3Area> villagetr(String url) {
        try {
            Thread.sleep(sleep);
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
                        C3Area c3Area = new C3Area();
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
