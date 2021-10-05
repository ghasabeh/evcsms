package devolon.fi.evcsms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:local_messages.properties")
public class EvcsmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvcsmsApplication.class, args);
    }

}
