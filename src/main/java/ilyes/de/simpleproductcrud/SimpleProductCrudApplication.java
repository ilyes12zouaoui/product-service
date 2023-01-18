package ilyes.de.simpleproductcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:configuration/enable_no_handler_found_exception_throwing.properties")
@PropertySource("classpath:openapi/open_api.properties")
public class SimpleProductCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleProductCrudApplication.class);
	}

}
