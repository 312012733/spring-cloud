package com.persistent.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(
{ FIELD }) // 表示该注解 能注解到类的 哪里（类，属性，方法）
@Retention(RetentionPolicy.RUNTIME) // 该注解类型要保留多久 ----- runtime 是指 整个运行期间
public @interface Colum {
    
    String name();
}
