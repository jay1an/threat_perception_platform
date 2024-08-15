package com.tpp.threat_perception_platform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyLog {
    /**
     * 模块标题
     */
    String title() default "";
    /**
     * 日志内容
     */
    String content() default "";
}
