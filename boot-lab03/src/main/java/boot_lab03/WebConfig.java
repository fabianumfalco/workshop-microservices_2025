package boot_lab03;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    @Bean
    public AlunoResourceAssembler alunoResourceAssembler() {
        return new AlunoResourceAssembler();
    }    
}