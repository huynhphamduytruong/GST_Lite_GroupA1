package com.gstlite.mobilestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gstlite.mobilestore.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageConfig.class
})
public class MobilestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobilestoreApplication.class, args);
	}

}
