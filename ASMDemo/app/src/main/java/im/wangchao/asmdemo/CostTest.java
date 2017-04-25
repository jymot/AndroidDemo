package im.wangchao.asmdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description  : CostTest.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/4/24.</p>
 * <p>Time         : 下午4:39.</p>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CostTest {
}
