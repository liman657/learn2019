package com.learn.mybatisplus.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import javafx.scene.control.Pagination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * autor:liman
 * createtime:2021/1/10
 * comment:
 */
@Configuration
public class PageSelectConfiguration {

    @Bean
    public PaginationInterceptor getPaginationInterceptor(){
        return new PaginationInterceptor();
    }

}
