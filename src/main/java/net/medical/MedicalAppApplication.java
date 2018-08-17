package net.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:image-config.properties")
public class MedicalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalAppApplication.class, args);
	}
}