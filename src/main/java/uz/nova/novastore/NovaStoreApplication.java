package uz.nova.novastore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Nova Store E-commerce",
                version = "1.0.1",
                description = "Nova new online shop",
                contact = @Contact(
                        name = "Support",
                        email = "novastore712@gmail.com"

                )
        )
)
@SecurityScheme(
        name = "jwtBearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class NovaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaStoreApplication.class, args);
    }

}
