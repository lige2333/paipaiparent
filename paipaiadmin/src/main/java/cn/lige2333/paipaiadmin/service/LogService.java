package cn.lige2333.paipaiadmin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "zuul")
public interface LogService {

    @RequestMapping("/log/query")
    @ResponseBody
    public String query();


}
