package main;

import main.entity.Application;
import main.repository.ApplicationRepository;
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
  public CommandLineRunner test(ApplicationRepository repository)
  {
    return args ->
    {
      repository.save(new Application("FirstApp", "My first app"));
      repository.save(new Application("SecondApp", "My second app"));

      for (Application app : repository.findAll())
      {
        log.info("The application is: " + app.toString());
      }
    };
  }

  private static final Logger log = LoggerFactory.getLogger(SpringApplicationWithPostgreSQL.class);
}
