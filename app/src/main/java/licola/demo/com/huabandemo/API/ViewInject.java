package licola.demo.com.huabandemo.API;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiYi on 2015/11/4 0004 15:00.
 *  注解类 弃用 用ButterKnife替换
 */

@Target(ElementType.FIELD)//作用在域上
@Retention(RetentionPolicy.RUNTIME) //表示在生命周期是在运行时
public @interface ViewInject {
    int value() default -1;

}
