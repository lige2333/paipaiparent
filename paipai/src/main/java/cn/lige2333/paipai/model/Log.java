package cn.lige2333.paipai.model;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String module() default "";

    String description() default "";
}
