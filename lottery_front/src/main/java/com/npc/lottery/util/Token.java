package com.npc.lottery.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 防止重复提交注解，用于方法上
 * 在需要生成token的controller上增加@Token(save=true)，而在需要检查重复提交的controller上添加@Token(
 * remove=true) 同时需要在新建的页面中添加 <input type="hidden" name="token"
 * value="${token}">
 * 
 * @author: Peter
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

	boolean save() default false;

	boolean remove() default false;
}