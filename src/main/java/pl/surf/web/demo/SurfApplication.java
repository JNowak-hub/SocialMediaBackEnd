package pl.surf.web.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.surf.web.demo.configuration.AppProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SurfApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurfApplication.class, args);
	}


}
