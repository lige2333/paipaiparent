package cn.lige2333.paipai;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@EnableEurekaClient
@EnableFeignClients
@MapperScan("cn.lige2333.paipai.dao")
@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
public class PaipaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaipaiApplication.class, args);
    }

}
