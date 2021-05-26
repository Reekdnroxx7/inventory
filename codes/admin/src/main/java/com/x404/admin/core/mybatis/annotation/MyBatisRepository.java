package com.x404.admin.core.mybatis.annotation;

import org.springframework.stereotype.Repository;

@Repository
public @interface MyBatisRepository {
    String value() default "";
}