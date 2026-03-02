package com.giudeMuslim.mauqetAlsallah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MauqetAlsallahApplication {

	public static void main(String[] args) {
		SpringApplication.run(MauqetAlsallahApplication.class, args);
	}

}
