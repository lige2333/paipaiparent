package cn.lige2333.paipaiadmin.config;

import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadBalanceConfig {
    @Bean
    public AbstractLoadBalancerRule rule(){
        AbstractLoadBalancerRule roundRobinRule = new RoundRobinRule();
        return roundRobinRule;
    }
}
