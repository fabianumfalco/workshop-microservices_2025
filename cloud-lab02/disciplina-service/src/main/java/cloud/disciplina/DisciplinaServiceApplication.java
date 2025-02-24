package cloud.disciplina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DisciplinaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisciplinaServiceApplication.class, args);
	}

}
