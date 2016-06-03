package lly.h5.android.test.hybrid;

/**
 * Created by leon on 16/5/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 用于标注与 js 交互的接口
 * @Field value js 调用的接口
 * @author zhangluya  2015/8/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JSAction {

    String value();
}
