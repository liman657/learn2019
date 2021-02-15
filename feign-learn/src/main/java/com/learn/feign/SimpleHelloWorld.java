package com.learn.feign;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
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
public class SimpleHelloWorld {

    public static final String API_URL = "https://api.github.com";

    public static void main(String[] args) throws IOException {

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        IGitHub github = retrofit.create(IGitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        List<Contributor> call = github.contributors("square", "retrofit");

        // Fetch and print a subMenuList of the contributors to the library.
        List<Contributor> contributors = call;
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }


}
