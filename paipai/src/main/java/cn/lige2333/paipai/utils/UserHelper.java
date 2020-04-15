package cn.lige2333.paipai.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserHelper {
    /**
     * 获取当前用户
     * @return
     */
    public static Authentication getCurrentUserAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     * @return
     */
    public static Object getCurrentPrincipal(){
        return getCurrentUserAuthentication().getPrincipal();
    }
}
