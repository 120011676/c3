package com.github.qq120011676.c3.data.impl;

import com.github.qq120011676.c3.data.DataCraw;
import com.github.qq120011676.c3.entity.C3Area;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataCrawBmcx implements DataCraw {
    private final String ua = "c3";

    @Override
    public List<C3Area> craw() {
        return parse(null);
    }

    public List<C3Area> parse(String c) {
        try {
            Thread.sleep(1000);
            String url = "https://xingzhengquhua.bmcx.com";
            if (Objects.nonNull(c)) {
                url += "/" + c + "__xingzhengquhua";
            }
            return Jsoup.connect(url)
                    .userAgent(ua)
                    .get()
                    .select("#main_content table[width=100%] tr")
                    .stream()
                    .filter(o -> o.childNodeSize() == 3)
                    .filter(o -> !Objects.equals("#F5F5F5",
                            o.childNodes().get(0).attr("bgcolor")))
                    .filter(o -> !o.childNodes().get(1).outerHtml().contains("*"))
                    .map(o -> {
                        List<Node> nodes = o.childNodes();
                        Node nameNode = Optional.of(nodes.get(0))
                                .map(v -> v.childNode(0))
                                .orElse(null);
                        String name = Optional.of(nameNode)
                                .map(v -> v.childNode(0))
                                .map(Node::outerHtml)
                                .orElse(null);
                        String code = Optional.of(nodes.get(1))
                                .map(v -> v.childNode(0))
                                .map(v -> v.childNode(0))
                                .map(Node::outerHtml)
                                .orElse(null);
                        String type = Optional.of(nodes.get(2))
                                .filter(v -> v.childNodeSize() > 0)
                                .map(v -> v.childNode(0))
                                .map(Node::outerHtml)
                                .orElse(null);
                        C3Area c3Area = new C3Area();
                        c3Area.setCode(code);
                        c3Area.setName(name);
                        c3Area.setType(type);
                        String nameStyle = nameNode.attr("style");
                        if (Objects.equals("", nameStyle)) {
                            c3Area.setChilds(parse(code));
                        }
                        return c3Area;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
