package im.wangchao.aopdemo;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <p>Description  : WeexAspect.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 17/3/10.</p>
 * <p>Time         : 上午10:43.</p>
 */
@Aspect
public class AspectTest {
    @Pointcut("within(@im.wangchao.aopdemo.Test *)")
    public void withinAnnotatedClass() {}

    @Pointcut("!withincode(* im.wangchao.aopdemo.Other.onCreate(..))")
    public void nonWithinOther(){}

    @Pointcut("execution(* *.onCreate(..)) && withinAnnotatedClass() && nonWithinOther()")
    public void onCreate(){}

    @Before("onCreate()")
    public void onCreate(JoinPoint joinPoint){
        Log.e("wcwcwc", joinPoint.toString() + "\n" + joinPoint.getThis() + "\n" + joinPoint.getTarget());
    }

}
