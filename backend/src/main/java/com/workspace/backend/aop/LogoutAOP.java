//package com.workspace.backend.aop;
//
//import com.workspace.backend.service.UserLogService;
//import jakarta.servlet.http.HttpSession;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LogoutAOP {
//    @Autowired
//    private UserLogService userLogService;
//    //提高作用域
//    private Object proceed;
//    private HttpSession session;
//    private int logId;
//    private String status;
//    @Pointcut("execution(* cn.wqk.springbootaop.controller.UserController.logout(..))")
//    public void pointCut(){}
//    //前置通知
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint){
//        System.out.println("前置通知-----------------------");
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        Object[] args=joinPoint.getArgs();
//        System.out.println("类："+className);
//        System.out.println("方法："+methodName);
//        System.out.println("传入参数：");
//        for (int i=0;i<args.length;i++){
//            System.out.println("参数"+(i+1)+"："+args[i]);
//        }
//        //第一个参数赋值给session，让我们能够从session中取到uid
//        session=(HttpSession)args[0];
//        //从session中获得uid
//        logId = (int) session.getAttribute("logId");
//        //从session中获得status
//        status = (String) session.getAttribute("status");
//        System.out.println("前置通知完--------------------");
//    }
//    @Around("pointCut()")
//    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("环绕通知：---------------------");
//        //获得方法执行后的返回值
//        proceed = pjp.proceed();
//        if (status.equals("login")) {
//            System.out.println("logId:"+logId);
//            userLogService.updateUserLog(logId);
//        }
//        /*if (proceed.equals("logout,success!")){
//            System.out.println("logId："+logId);
//            userLogService.updateUserLog(logId);
//        }*/
//        System.out.println("执行的方法后的返回值："+proceed+"");
//        System.out.println("环绕通知完--------------------");
//        return proceed;
//    }
//}