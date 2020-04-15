package cn.lige2333.message.Service;

import cn.lige2333.message.Dao.MessageDao;
import cn.lige2333.message.Entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public void sendMsg(Message message) {
        messageDao.insert(message);
    }

    public List<Message> findByReceiver(Integer toId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("toid", toId);
        example.and(criteria);
        List<Message> messages = messageDao.selectByExample(example);
        return messages;
    }
}
