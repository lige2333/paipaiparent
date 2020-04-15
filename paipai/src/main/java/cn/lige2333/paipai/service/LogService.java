package cn.lige2333.paipai.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "log")
public interface LogService {

    @RequestMapping("/put")
    @ResponseBody
    public String put(@RequestParam("module") String module,@RequestParam("description") String description,@RequestParam("username")String username);


}
