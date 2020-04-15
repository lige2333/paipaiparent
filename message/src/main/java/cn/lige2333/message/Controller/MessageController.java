package cn.lige2333.message.Controller;

import cn.lige2333.message.Entity.Message;
import cn.lige2333.message.Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;
    @RequestMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message logs = mapper.readValue(message, new TypeReference<Message>() {
        });
        messageService.sendMsg(logs);
        return "success";
    }
    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll(Integer toId){
        try {
            List<Message> all = messageService.findByReceiver(toId);
            System.out.println(all);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String s = mapper.writerFor(new TypeReference<List<Message>>() {
            }).writeValueAsString(all);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return "日志写入错误！";
        }

    }
}
