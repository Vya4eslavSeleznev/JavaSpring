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
public class SpringApplicationWithPostgreSQL
{
  public static void main (String[] args)
  {
    SpringApplication.run(SpringApplicationWithPostgreSQL.class, args);
  }

  @Bean
  public CommandLineRunner test(OperationRepository repository)
  {
    return args ->
    {
      repository.save(new Operation(1, 2, 3, 4, "123", 6));
      repository.save(new Operation(11, 22, 33, 44, "112233", 66));

      for (Operation app : repository.findAll())
      {
        log.info("The application is: " + app.toString());
      }
    };
  }

  private static final Logger log = LoggerFactory.getLogger(SpringApplicationWithPostgreSQL.class);
}
