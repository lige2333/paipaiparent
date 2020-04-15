package cn.lige2333.paipai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Autowired
    private RestTemplateBuilder bdr;

    @Bean
    public RestTemplate restTemplate(){
        return bdr.build();
    }



}
