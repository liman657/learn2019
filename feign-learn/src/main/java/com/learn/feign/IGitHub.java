package com.learn.feign;

import feign.Body;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:
 * https://github.com/OpenFeign/feign
 */
public interface IGitHub {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    @RequestLine("POST /repos/{owner}/{repo}/issues")
    void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);

}
