package com.companies.house.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Lazy
@Autowired
@Retention(RetentionPolicy.RUNTIME)
public @interface LazyAutowired {
}
