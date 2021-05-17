package com.x404.beat.core.mybatis.annotation;

import org.springframework.stereotype.Repository;

@Repository
public @interface MyBatisRepository {
    String value() default "";
}