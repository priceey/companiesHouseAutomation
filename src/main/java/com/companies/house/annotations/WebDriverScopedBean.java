package com.companies.house.annotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Bean
@Scope("webdriverscope")
@Retention(RetentionPolicy.RUNTIME)
public @interface WebDriverScopedBean {
}
