package cn.lige2333.paipaiadmin.service;

import cn.lige2333.paipaiadmin.Entity.Bid;
import cn.lige2333.paipaiadmin.Entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "paipai")
public interface UsersService {

    @RequestMapping("/getAllUser")
    @ResponseBody
    public String getAllUser();

    @RequestMapping("/setState")
    @ResponseBody
    public String setState(@RequestParam("id")Integer id, @RequestParam("status")Integer status);

    @ResponseBody
    @RequestMapping("/queryAllPros")
    public String queryAllPros();

    @ResponseBody
    @RequestMapping("/queryProsByState")
    public String queryProsByState(@RequestParam("state") Integer state) throws JsonProcessingException;

    @ResponseBody
    @RequestMapping("/getBidsById")
    String getBidsById(Integer proId);

    @RequestMapping("/changeProState")
    @ResponseBody
    public String changeProState(@RequestParam("id")Integer id, @RequestParam("state")Integer state);
}
