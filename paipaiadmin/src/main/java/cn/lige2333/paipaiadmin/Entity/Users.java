package cn.lige2333.paipaiadmin.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Users implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String telephone;

    private Integer status;

    private String state;

    public String getState() {
        if(status==0){
            return "正常使用";
        }else if(status==1){
            return "已禁用";
        }else{
            return "未知状态！";
        }
    }
}
