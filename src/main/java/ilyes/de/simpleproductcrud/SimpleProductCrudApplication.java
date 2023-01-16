package ilyes.de.simpleproductcrud;

import ilyes.de.simpleproductcrud.config.log.logeventfactory.MaskSensitiveDataLogEventFactory;
import org.apache.logging.log4j.core.util.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:configuration/enable_no_handler_found_exception_throwing.properties")
@PropertySource("classpath:configuration/open_api.properties")
@PropertySource("classpath:configuration/logs/log4j2.properties")
public class SimpleProductCrudApplication {

	public static void main(String[] args) {
		//Register Log4j LogEventFactory implementation which intercept the log for modification before logging.
		System.setProperty(Constants.LOG4J_LOG_EVENT_FACTORY, MaskSensitiveDataLogEventFactory.class.getName());
		SpringApplication.run(SimpleProductCrudApplication.class);
	}

}
