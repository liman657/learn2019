package com.learn.springbootstarter.AutoImportSelector;

import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
public class SomeServiceImport implements ImportSelector {

    private String excludeClassName="com.learn.springbootstarter.AutoImportSelector.CacheService";

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        String name = EnableDefineService.class.getName();
        //将注解中的元数据转换成attributes
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(name, true));
        //获取注解元数据中的值
        String[] excludes = (String[]) attributes.get("exclude");
        String excludeName = excludes[0];//获得需要屏蔽加载的类;
        String[] services=null;
        if(excludeClassName.equals(excludeName)){
            services = new String[]{LoggerService.class.getName()};
        }else{
            services = new String[]{CacheService.class.getName()};
        }
        return services;
    }
}
