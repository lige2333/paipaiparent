package cn.lige2333.aliserver;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@EnableScheduling
@MapperScan("cn.lige2333.aliserver.Dao")
@EnableRabbit
@SpringBootApplication
public class AliserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliserverApplication.class, args);
    }

}
