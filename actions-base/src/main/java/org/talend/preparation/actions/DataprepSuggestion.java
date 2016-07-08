package org.talend.preparation.actions;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
public @interface DataprepSuggestion {

}
