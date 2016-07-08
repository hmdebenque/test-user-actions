package org.talend.dataprep.transformation.actions.tests;

import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.talend.preparation.actions.DataprepAction;

import java.util.Collections;

@Profile("standalone")
@EnableAutoConfiguration
@ComponentScan(value = "com.user.custom.actions", basePackageClasses = Application.class)
@Configuration
public class Application {

    @SuppressWarnings("all")
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CustomAutowireConfigurer customAutowireConfigurer(){
        CustomAutowireConfigurer customAutowireConfigurer = new CustomAutowireConfigurer();
        customAutowireConfigurer.setCustomQualifierTypes(Collections.singleton(DataprepAction.class));
        return customAutowireConfigurer;
    }


//    @Bean
//    public org.talend.dataprep.transformation.actions.text.UserAction2 userAction1() {
//        return new org.talend.dataprep.transformation.actions.text.UserAction2();
//    }
//
//    @Bean
//    public UserAction2 userAction2() {
//        return new UserAction2();
//    }

}
