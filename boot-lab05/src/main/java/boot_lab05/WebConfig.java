package boot_lab05;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(true) // Permite JSON/XML via parâmetro na URL
            .parameterName("mediaType")
            .ignoreAcceptHeader(true) // Permite negociação via "Accept"
            .defaultContentType(MediaType.APPLICATION_JSON) // JSON é o formato padrão
            .mediaType("json", MediaType.APPLICATION_JSON);
    }
    
}