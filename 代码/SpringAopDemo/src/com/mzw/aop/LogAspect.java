package com.mzw.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
/**
 * 日志切面管理
 */
@Component
@Aspect     //一个切面
public class LogAspect {
    /**
     * @Before  通知的类型（前置通知），目标方法之前执行的方法
     * execution    设置切入点知道对哪个方法做前置通知操作
     * 第一个*代表方法返回值
     * 第二个*代表类名称
     * 第三个*代表方法名称
     * 最后（..）代表所有参数
     */
    @Before(value ="execution(* com.mzw.service.impl.UserServiceImpl.*(..))")
    public void before(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();//得到方法所有参数信息
//        for (Object obg :args){
//            System.out.print(obg+"\t");
//        }
        System.out.println();
        String methodName = joinPoint.getSignature().getName(); //方法名称
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("日志：------->前置通知："+name+"\t"+methodName);
    }

    /**
     * 后置通知
     * @param joinPoint
     */
    @After(value = "execution(* com.mzw.service.impl.UserServiceImpl.*(..))")
    public void after(JoinPoint joinPoint){

        String methodName = joinPoint.getSignature().getName(); //方法名称
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("日志：------->后置通知："+name+"\t"+methodName);
    }

    /****
     * 返回后通知( After returning advice) :** 在一个连接点正常完成后执行的通知。例如: -一个方法正常返回,没有抛出任何异常。
     *  value 切入点的表达式
     *  pointcur 切入点的返回值
     *  returning：String 返回值定义的别名
     */
    @AfterReturning(pointcut = "execution(* com.mzw.service.impl.UserServiceImpl.login(..))",returning = "result")
    public void afterReturn(JoinPoint joinPoint,Object result){//返回参数名称要和上面的一致
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        if (result!=null){
            System.out.println("【返回后通知】----》登陆成功");
        }else{
            System.out.println("【返回后通知】----》用户名或密码错误");
        }
    }

    /***
     * 异常通知：方法出现异常会执行
     * throwing ：一场别名，必须和方法名一样
     * 可以通过改变Exception的异常类型，来对特定异常执行响应代码
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(pointcut="execution(* com.mzw.service.impl.UserServiceImpl.testException(..))",throwing="exception")
    public void afterThrowing(JoinPoint joinPoint, NullPointerException exception){
        if (exception!=null){
            System.out.println("【异常通知】----》"+joinPoint.getTarget().getClass().getName()+"\t"+joinPoint.getSignature().getName()+"\t"+exception);
        }
    }

    /***
     * 环绕通知
     * 环绕通知(Around advice) :包围一个连接点的通知，就像方法调用。
     * 这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。
     * 它也会选择是否继续执行连接点或直接返回他们自己的返回值域或抛出异常来结束执行
     */
    @Around(value="execution(* com.mzw.service.impl.UserServiceImpl.selectById(..))")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Object object = null;
        System.out.println("前置通知");
        try {
            object = proceedingJoinPoint.proceed();
            System.out.println("后置通知");
        } catch (Throwable throwable) {
            //抛出后通知
            System.out.println("抛出后通知");
            throwable.printStackTrace();
        }
        System.out.println("返回后通知");
    }

}
