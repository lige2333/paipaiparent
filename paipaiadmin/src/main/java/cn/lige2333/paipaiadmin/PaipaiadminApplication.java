package cn.lige2333.paipaiadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableFeignClients
@MapperScan("cn.lige2333.paipaiadmin.mapper")
@EnableEurekaClient
@SpringBootApplication
public class PaipaiadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaipaiadminApplication.class, args);
    }

}
