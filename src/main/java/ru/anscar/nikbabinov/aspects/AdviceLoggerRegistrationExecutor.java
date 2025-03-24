package ru.anscar.nikbabinov.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)

public class AdviceLoggerRegistrationExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AdviceLoggerRegistrationExecutor.class);

    @Before("ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceRegisterUser()")
    public void logBeforeRegisterUser(JoinPoint joinPoint) {
        logger.info("isServiceRegisterArguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            value = "ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceRegisterUser()",
            returning = "returnValue"
    )
    public void logAfterRegisterUser(JoinPoint joinPoint, Object returnValue) {
        logger.info("isServiceRegistrationUser return: {}", returnValue);
    }

    @AfterThrowing(
            value = "ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceRegisterUser()",
            throwing = "error"
    )
    public void logAfterThrowingRegistrationUser(JoinPoint joinPoint, Throwable error) {
        logger.error("isServiceRegistrationUser error: {}", error.getMessage());
    }
}
