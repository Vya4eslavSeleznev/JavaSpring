package main;

import main.entity.Operation;
import main.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringApplicationWithPostgreSQL {
  private static final Logger log = LoggerFactory.getLogger(SpringApplicationWithPostgreSQL.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringApplicationWithPostgreSQL.class, args);
  }

  @Bean
  public CommandLineRunner test(OperationRepository repository) {
    return args -> {
      Iterable<Operation> temp = repository.findAll();

      for(Operation temp2 : temp) {
        log.info("The application is: " + temp2.getId());
      }
    };
  }
}
