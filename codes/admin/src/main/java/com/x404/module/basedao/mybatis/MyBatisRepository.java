package com.x404.module.basedao.mybatis;

import org.springframework.stereotype.Repository;

@Repository
public @interface MyBatisRepository {
    String value() default "";
}