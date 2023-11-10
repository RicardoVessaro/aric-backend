package rv.aric;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rv.aric.ipsum.enums.EnumEntityRunner;

import java.util.List;

@SpringBootApplication
public class AricApplication {

	public static void main(String[] args) {
		SpringApplication.run(AricApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(List<EnumEntityRunner> enumEntityRunners) {
		return runner -> {
			for(EnumEntityRunner enumEntityRunner: enumEntityRunners) {
				enumEntityRunner.run();
			}
		};
	}

}
