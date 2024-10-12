package com.eunsun.mssproductapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication
public class MssProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MssProductApiApplication.class, args);
    }

}
