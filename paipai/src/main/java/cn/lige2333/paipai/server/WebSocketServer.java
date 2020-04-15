package cn.lige2333.paipai.server;

import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.utils.UserHelper;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
    private static ConcurrentHashMap<Session,String> stringSessionConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        SessionSet.add(session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        SendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        stringSessionConcurrentHashMap.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);
        SendMessage(session, "收到消息，消息内容："+message);
        if(message.startsWith("用户名@")){
            String[] split = message.split("@");
            stringSessionConcurrentHashMap.put(session,split[1]);
        }
        if(message.startsWith("消息!@")){
            String[] split = message.split("!@");
            String sender = split[1];
            String receiver=split[2];
            String messages="";
            if(split.length==4){
                messages=split[3];
            }else{
                for (int i=3;i<split.length;i++){
                    messages+=split[i];
                    if(i!=split.length-1){
                        messages+="!@";
                    }
                }
            }
            ConcurrentHashMap.KeySetView<Session, String> sessions = stringSessionConcurrentHashMap.keySet();
            for (Session session1 : sessions) {
                if(stringSessionConcurrentHashMap.get(session1).equals(receiver)){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("sender", sender);
                    map.put("receiver", receiver);
                    map.put("messages",messages);
                    map.put("type", "msg");
                    String s = JSON.toJSONString(map);
                    SendMessage(session1, s);
                }
            }


        }
    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<String> getCurrentUsers() {
        List<String> usernames = new ArrayList<>();
        Collection<String> values = stringSessionConcurrentHashMap.values();
        Iterator<String> iterator = values.iterator();
        while (iterator.hasNext()){
            usernames.add(iterator.next());
        }
        return usernames;
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if(session.isOpen()){
                SendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String sessionId,String message) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session!=null){
            SendMessage(session, message);
        }
        else{
            log.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }

}