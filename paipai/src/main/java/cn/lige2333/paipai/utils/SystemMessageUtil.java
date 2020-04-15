package cn.lige2333.paipai.utils;

import cn.lige2333.paipai.entity.Message;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Date;
import java.util.List;

public class SystemMessageUtil {
    public static void sendToOne(String content, Integer toId, String receiver, MessageService ms) throws JsonProcessingException {
        Message message = new Message();
        message.setContent(content);
        message.setFromid(0);
        message.setFromname("系统");
        message.setStatus("未读");
        message.setToname(receiver);
        message.setToid(toId);
        message.setTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String s = mapper.writerFor(new TypeReference<Message>() {
        }).writeValueAsString(message);
        ms.put(s);
    }
    public static void sendToUsers(String content, MessageService ms, List<User> users) throws JsonProcessingException {
        for (User user:users){
            sendToOne(content, user.getId(), user.getUsername(), ms);
        }

    }
}
