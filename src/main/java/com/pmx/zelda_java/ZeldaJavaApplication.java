package com.pmx.zelda_java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.pmx.zelda_java.appinfo.mapper")
public class ZeldaJavaApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZeldaJavaApplication.class, args);
    }

}
