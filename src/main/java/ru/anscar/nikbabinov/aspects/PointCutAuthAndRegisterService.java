package ru.anscar.nikbabinov.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class PointCutAuthAndRegisterService {

    @Pointcut("execution(public * ru..RegisterService.registerUser(*))")
    public void isServiceRegisterUser(){}

    @Pointcut("execution(public * ru..AuthService.loginUser(*))")
    public void isServiceLoginUser(){}
}
