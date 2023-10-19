package com.github.qq120011676.c3.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChinaCityCode {
    private String code;
    private String name;
    private List<ChinaCityCode> childs;
}
