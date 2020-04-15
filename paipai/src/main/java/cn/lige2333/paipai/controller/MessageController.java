package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.entity.Message;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.service.MessageService;
import cn.lige2333.paipai.service.UserService;
import cn.lige2333.paipai.utils.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/send")
    public String send(String receiver,String content) throws JsonProcessingException {
        if(!userService.checkUsername(receiver)){
            return "接收者用户名不存在！";
        }
        Message message = new Message();
        User currentUser = (User) UserHelper.getCurrentPrincipal();
        message.setContent(content);
        message.setFromid(currentUser.getId());
        message.setFromname(currentUser.getUsername());
        message.setToid(userService.getUserId(receiver));
        message.setStatus("未读");
        message.setToname(receiver);
        message.setTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = mapper.writerFor(new TypeReference<Message>() {
        }).writeValueAsString(message);
        messageService.put(s);
        return "success";
    }
}
