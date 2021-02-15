package com.learn.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment: feign-helloworld
 */
@Slf4j
public class SimpleHelloWorldUpate {

    public static final String API_URL = "https://api.github.com";

    public static void main(String[] args) throws IOException {
        IGitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(IGitHub.class, API_URL);

        // Fetch and print a subMenuList of the contributors to this library.
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");

        contributors.stream().forEach(c->{
            System.out.println(c.login+":"+c.contributions);
        });

//        for (Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }
    }


}
