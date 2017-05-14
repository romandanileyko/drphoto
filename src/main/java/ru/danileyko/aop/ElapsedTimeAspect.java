package ru.danileyko.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by danil on 14.05.2017.
 */
@Aspect
@Component
public class ElapsedTimeAspect {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @After("execution(* ru.danileyko.controller.IndexController.*(..))")
    public void postControllerMethod(JoinPoint joinPoint){
        logger.info("CALLED AFTER METHOD "+joinPoint.getSignature().getName());
    }

    @Around("execution(* ru.danileyko.controller.IndexController.adminPage(..)))")
    public Object adminPageTest(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("Going to call the method.");
        Object output = joinPoint.proceed();
        System.out.println("Method execution completed.");
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Method execution time: " + elapsedTime + " milliseconds.");
        return output;
    }

    @Around("execution(* ru.danileyko.controller.IndexController.loadPage(..))")
    public Object loadPageTest(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("Going to call the method.");
        Object output = joinPoint.proceed();
        System.out.println("Method execution completed.");
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Method execution time: " + elapsedTime + " milliseconds.");
        return output;
    }
}
