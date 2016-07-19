package com.smorales.javalab.business.files;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@InterceptorBinding
@Target({METHOD})
@Retention(RUNTIME)
@Inherited
public @interface Async {
}