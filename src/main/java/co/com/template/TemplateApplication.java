package co.com.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"co.com.template.Repositories.entities"})
@EnableJpaRepositories(basePackages = {"co.com.template.Repositories"})
@ComponentScan(basePackages = {"co.com.template.Controllers", "co.com.template.services", "co.com.template.settings", "co.com.template.utils","co.com.template.security"})
public class TemplateApplication {
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

}
