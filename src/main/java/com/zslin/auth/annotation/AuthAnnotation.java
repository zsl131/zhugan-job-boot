package com.zslin.auth.annotation;

/**
 * Created by zsl on 2018/10/29.
 */
public @interface AuthAnnotation {

    String name();

    String code();

    String params() default "";
}
