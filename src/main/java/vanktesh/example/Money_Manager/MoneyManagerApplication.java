package vanktesh.example.Money_Manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoneyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyManagerApplication.class, args);
	}

}
