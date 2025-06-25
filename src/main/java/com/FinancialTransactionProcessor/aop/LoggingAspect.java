package com.FinancialTransactionProcessor.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.FinancialTransactionProcessor.service_impls..*(..))")
    public void serviceImplMethods() {}

    @Around("serviceImplMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("Entering {} with arguments: {}", methodName, Arrays.toString(args));

        Object result = null;
        try {
            result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("Exiting {} with result: {} (Execution time: {} ms)", methodName, result, duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("Exception in {} after {} ms: {}", methodName, duration, ex.getMessage(), ex);
            throw ex;
        }
    }
}
