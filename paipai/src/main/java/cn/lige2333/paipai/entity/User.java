package cn.lige2333.paipai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String password;

    private String telephone;

    private Integer status;

    private String state;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private boolean accountNonExpired;

    @JsonIgnore
    private boolean accountNonLocked;

    @JsonIgnore
    private boolean credentialsNonExpired;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private String realPassword;

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRealPassword(String realPassword) {
        this.realPassword = realPassword;
    }

    private static final long serialVersionUID = 1L;

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(getStatus()==0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority("ROLE_VIP1"));
        authorities.add(new SimpleGrantedAuthority("ROLE_VIP2"));
        authorities.add(new SimpleGrantedAuthority("ROLE_VIP3"));
        return authorities;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return new BCryptPasswordEncoder().encode(password);
    }

    public String getRealPassword() {
        return password;
    }
    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
    public String getState() {
        if(status==null){
            return "未知状态！";
        }else if(status==0){
            return "正常使用";
        }else if(status==1){
            return "已禁用";
        }else{
            return "未知状态！";
        }
    }
}