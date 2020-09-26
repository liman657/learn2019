package com.learn.springauthserver.config;

import com.learn.springauthserver.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * autor:liman
 * createtime:2019/12/13
 * comment:
 */
@Configuration
public class CustomerWebConfig implements WebMvcConfigurer {

    //添加拦截器

    /**
     * 每一个类型的拦截器需要单独添加，不可串行添加
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final String[] interceptorPaths = new String[]{"/dbAuth/token/auth"
                , "/dbAuth/token/password/update", "/dbAuth/token/logout"};

        final String[] redisInterceptorPath = new String[]{"/redisAuth/token/auth"
                , "/redisAuth/token/password/update", "/redisAuth/logout"};

        final String[] jwtInterceptorPath = new String[]{"/JWTAuth/token/auth"
                , "/JWTAuth/token/password/update", "/JWTAuth/token/logout"};

        final String[] sessionInterceptor = new String[]{"/session/auth"
                , "/session/password/update", "/session/logout"};

        registry.addInterceptor(dbAuthInterceptor())
                .addPathPatterns(interceptorPaths)
                .excludePathPatterns(redisInterceptorPath);
//
//        registry.addInterceptor(redisAuthInterceptor())
//                .addPathPatterns(redisInterceptorPath).excludePathPatterns(interceptorPaths);
//
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns(jwtInterceptorPath)
//                .excludePathPatterns(redisInterceptorPath)
//                .excludePathPatterns(interceptorPaths);

//        registry.addInterceptor(sessionInterceptor()).addPathPatterns(sessionInterceptor);
    }

    @Bean
    public DBAuthInterceptor dbAuthInterceptor() {
        return new DBAuthInterceptor();
    }

//    @Bean
//    public RedisTokenAuthInterceptor redisAuthInterceptor() {
//        return new RedisTokenAuthInterceptor();
//    }

//    @Bean
//    public JWTInterceptor jwtInterceptor() {
//        return new JWTInterceptor();
//    }

//    @Bean
//    public JWTRedisInterceptor jwtRedisInterceptor(){
//        return new JWTRedisInterceptor();
//    }

//    @Bean
//    public SessionInterceptor sessionInterceptor(){
//        return new SessionInterceptor();
//    }

//    //访问静态资源
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//    }
//
//    //跨域设置
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("*");
//    }
}
