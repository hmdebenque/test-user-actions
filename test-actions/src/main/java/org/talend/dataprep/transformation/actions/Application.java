package org.talend.dataprep.transformation.actions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("standalone")
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = Application.class)
public class Application {

    @SuppressWarnings("all")
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

}
