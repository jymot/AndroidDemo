package im.wangchao.aopdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description  : Weex.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/3/10.</p>
 * <p>Time         : 下午2:56.</p>
 */
@Target({
        ElementType.TYPE
})
@Retention(RetentionPolicy.CLASS)
public @interface Test {
}
