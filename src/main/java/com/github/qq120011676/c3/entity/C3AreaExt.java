package com.github.qq120011676.c3.entity;

import lombok.Data;

import java.util.List;

@Data
public class C3AreaExt {
    private String name;
    private String shortName;
    private String code;
    private String type;
    private String postalCode;
    private List<C3AreaExt> childs;
    private String url;
}
