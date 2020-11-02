package in.dagoan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DagoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DagoanApplication.class, args);
	}
}
