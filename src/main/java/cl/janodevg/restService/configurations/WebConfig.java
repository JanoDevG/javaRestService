package cl.janodevg.restService.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CustomErrorAttributes customErrorAttributes;

    @Bean
    public ErrorAttributes errorAttributes() {
        return customErrorAttributes;
    }

}
