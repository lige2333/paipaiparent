package cn.lige2333.paipai.service;

import cn.lige2333.paipai.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "message")
public interface MessageService {

    @RequestMapping("/sendMessage")
    @ResponseBody
    public String put(@RequestParam("message") String message);

    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll(@RequestParam("toId") Integer toId);

}
