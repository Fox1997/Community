package com.example.comm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.comm.mapper")
public class
CommApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommApplication.class, args);
    }

}
