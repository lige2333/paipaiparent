package cn.lige2333.log.controller;

import cn.lige2333.log.entity.Logs;
import cn.lige2333.log.service.LogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LogController {
    @Autowired
    private LogService logService;
    @RequestMapping("/put")
    public String put(String module, String description,String username){
        try {
            if (StringUtils.isEmpty(username)) {
                username ="未知用户";
            }
            Logs log = new Logs();
            log.setUsername(username);
            log.setModule(module);
            log.setDescription(description);
            log.setCreatetime(new Date());
            logService.saveLog(log);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "日志写入错误！";
        }

    }
    @RequestMapping("/query")
    public String query(){
        try {
            List<Logs> all = logService.findAll();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String s = mapper.writerFor(new TypeReference<List<Logs>>() {
            }).writeValueAsString(all);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return "日志写入错误！";
        }

    }
}
