package com.kuo.huahua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Channing Kuo
 * @date 2021/06/28
 */
@SpringBootApplication
@MapperScan("com.kuo.huahua.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
