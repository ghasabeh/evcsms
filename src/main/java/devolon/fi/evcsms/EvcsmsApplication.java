package devolon.fi.evcsms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@OpenAPIDefinition(
        info = @Info(
                title = "The Electric Vehicle Charging Station Management System",
                version = "0.0.1-SNAPSHOT",
                description = "This is Electric Vehicle Charging Station Management System. We should manage " +
                        "charging companies. every company can have child companies. It has stations too. every stations" +
                        "belongs to one company. In fact they have hierarchy. For example, we got 3 companies A, B and C" +
                        " accordingly with 10,5 and 2 stations. Company B belongs to A and company C belongs to B. " +
                        "Then we can say that company A has 17, company B has 7 and company C has 2 stations in total.",
                contact = @Contact(
                        name = "Alireza Ghasabeie",
                        email = "a.ghasabeh@gmail.com"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "This is test not real license : Apache 2.0"
                )
        ),
        security = {
                @SecurityRequirement(
                        name = "nothing"
                )
        }
)
@SpringBootApplication
@PropertySource("classpath:local_messages.properties")
public class EvcsmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvcsmsApplication.class, args);
    }

}
