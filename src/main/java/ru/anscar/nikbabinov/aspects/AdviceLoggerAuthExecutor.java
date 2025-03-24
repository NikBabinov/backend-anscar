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
public class AdviceLoggerAuthExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AdviceLoggerRegistrationExecutor.class);

    @Before(
            value = "ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceLoginUser()"
    )
    public void logBeforeLoginUser(JoinPoint joinPoint) {
        logger.info("isServiceLoginUserArguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            value = "ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceLoginUser()",
            returning = "returnValue"
    )
    public void logAfterLoginUser(JoinPoint joinPoint, Object returnValue) {
        logger.info("isServiceLoginUser return: {}", returnValue);
    }

    @AfterThrowing(
            value = "ru.anscar.nikbabinov.aspects.PointCutAuthAndRegisterService.isServiceLoginUser()",
            throwing = "error"
    )
    public void logAfterThrowingLoginUser(JoinPoint joinPoint, Throwable error) {
        logger.error("isServiceLoginUser error: {}", error.getMessage());
    }
}
