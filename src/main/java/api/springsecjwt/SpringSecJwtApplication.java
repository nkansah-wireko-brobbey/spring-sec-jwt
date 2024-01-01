package api.springsecjwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecJwtApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecJwtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started.");
    }

}
