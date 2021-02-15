package com.learn.feign;

import lombok.Data;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:
 */
@Data
public class Issue {
    String title;
    String body;
    List<String> assignees;
    int milestone;
    List<String> labels;
}
