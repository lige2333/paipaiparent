package cn.lige2333.log.service;

import cn.lige2333.log.dao.LogMapper;
import cn.lige2333.log.entity.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;
    public void saveLog(Logs log){
        logMapper.insert(log);
    }

    public List<Logs> findAll() {
        List<Logs> logs = logMapper.selectAll();
        return logs;
    }
}
