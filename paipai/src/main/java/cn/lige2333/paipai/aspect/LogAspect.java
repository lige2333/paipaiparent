package cn.lige2333.paipai.aspect;

import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.model.Log;
import cn.lige2333.paipai.service.LogService;
import cn.lige2333.paipai.utils.UserHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.lige2333.paipai.model.Log)")
    public void logPointCut(){}

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfter(JoinPoint joinPoint){
        /**
         * 解析Log注解
         */
        String methodName = joinPoint.getSignature().getName();
        Method method = currentMethod(joinPoint,methodName);
        Log log = method.getAnnotation(Log.class);
        try{
            User user = (User) UserHelper.getCurrentPrincipal();
            logService.put(log.module(),log.description(),user.getUsername());
        }catch (Exception e){
            logService.put(log.module(),log.description(),null);
        }

    }

    /**
     * 获取当前执行的方法
     *
     * @param joinPoint  连接点
     * @param methodName 方法名称
     * @return 方法
     */
    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /**
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

}
