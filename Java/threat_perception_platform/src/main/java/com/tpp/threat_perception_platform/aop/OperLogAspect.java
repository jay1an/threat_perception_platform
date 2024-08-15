package com.tpp.threat_perception_platform.aop;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.pojo.LoginUser;
import com.tpp.threat_perception_platform.pojo.OperLog;
import com.tpp.threat_perception_platform.service.OperLogService;
import com.tpp.threat_perception_platform.utils.JwtUtil;
import com.tpp.threat_perception_platform.utils.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 切面处理类，记录操作日志到数据库
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private OperLogService operLogService;

    @Autowired
    private RedisCache redisCache;

    @Pointcut("@annotation(com.tpp.threat_perception_platform.annotation.MyLog)")
    public void operaLogPointCut() {}

    @Pointcut("@annotation(com.tpp.threat_perception_platform.annotation.LoginLog)")
    public void loginLogPointCut() {}

    @Pointcut("@annotation(com.tpp.threat_perception_platform.annotation.LogoutLog)")
    public void logoutLogPointCut() {}

    @Pointcut("@annotation(com.tpp.threat_perception_platform.annotation.PasswordChangeLog)")
    public void passwordChangeLogPointCut() {}

    @AfterReturning(value = "operaLogPointCut()", returning = "result")
    public void saveOperaLog(JoinPoint joinPoint, Object result) {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog operlog = new OperLog();
            MyLog myLog = method.getAnnotation(MyLog.class);
            if (myLog != null) {
                operlog.setTitle(myLog.title());//设置模块名称
                operlog.setContent(myLog.content());//设置日志内容
            }
            // 将入参转换成json
            String params = argsArrayToString(joinPoint.getArgs());
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName + "()";
            operlog.setMethod(methodName); //设置请求方法
            operlog.setRequestMethod(request.getMethod());//设置请求方式
            operlog.setRequestParam(params); // 请求参数
            operlog.setResponseResult(JSON.toJSONString(result)); // 返回结果
            // 获取用户名（从token中解析)
            String token = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJWT(token);
            String uuid = claims.getSubject();
            // 从redis中获取用户信息
            String redisKey = "login_" + uuid;
            LoginUser loginUser = JSON.parseObject(redisCache.getCacheObject(redisKey), LoginUser.class);
            operlog.setOperName(loginUser.getUsername());

            operlog.setIp(getIp(request)); // IP地址
            operlog.setRequestUrl(request.getRequestURI()); // 请求URI
            operlog.setOperTime(new Date()); // 时间
            operlog.setStatus(0);//操作状态（0正常 1异常）  // 这里只有正常的状态...
            Long takeTime = 0L;
            operlog.setTakeTime(takeTime);
            //插入数据库
            operLogService.add(operlog);
        } catch (Exception e) {
            System.out.println("================================");
            e.printStackTrace();
        }
    }

    @Around("logoutLogPointCut()")
    public Object saveLogoutLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperLog operlog = new OperLog();
        // 设置默认值
        operlog.setTitle("用户模块");
        operlog.setContent("用户登出");

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName + "()";
        operlog.setMethod(methodName); // 设置请求方法

        // 将入参转换成json
        String params = argsArrayToString(joinPoint.getArgs());
        operlog.setRequestParam(params); // 请求参数
        operlog.setRequestMethod(request.getMethod());//设置请求方式

        // 从请求头获取 token
        String token = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJWT(token);
        String uuid = claims.getSubject();
        // 从 redis 中获取用户信息
        String redisKey = "login_" + uuid;
        LoginUser loginUser = JSON.parseObject(redisCache.getCacheObject(redisKey), LoginUser.class);
        operlog.setOperName(loginUser.getUsername());

        operlog.setIp(getIp(request)); // IP地址
        operlog.setRequestUrl(request.getRequestURI()); // 请求URI
        operlog.setOperTime(new Date()); // 时间

        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            operlog.setStatus(0); // 设置操作状态正常
        } catch (Throwable throwable) {
            operlog.setStatus(1); // 设置操作状态异常
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            operlog.setTakeTime(endTime - startTime); // 设置执行时间
            // 设置返回结果
            operlog.setResponseResult(JSON.toJSONString(result));
            // 插入数据库
            operLogService.add(operlog);
        }
        return result;
    }


    @Around("passwordChangeLogPointCut()")
    public Object savePasswordChangeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperLog operlog = new OperLog();
        // 设置默认值
        operlog.setTitle("用户模块");
        operlog.setContent("修改密码");

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName + "()";
        operlog.setMethod(methodName); // 设置请求方法

        // 将入参转换成json
        String params = argsArrayToString(joinPoint.getArgs());
        operlog.setRequestParam(params); // 请求参数
        operlog.setRequestMethod(request.getMethod());//设置请求方式

        // 从请求头获取 token
        String token = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJWT(token);
        String uuid = claims.getSubject();
        // 从 redis 中获取用户信息
        String redisKey = "login_" + uuid;
        LoginUser loginUser = JSON.parseObject(redisCache.getCacheObject(redisKey), LoginUser.class);
        operlog.setOperName(loginUser.getUsername());

        operlog.setIp(getIp(request)); // IP地址
        operlog.setRequestUrl(request.getRequestURI()); // 请求URI
        operlog.setOperTime(new Date()); // 时间

        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            operlog.setStatus(0); // 设置操作状态正常
        } catch (Throwable throwable) {
            operlog.setStatus(1); // 设置操作状态异常
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            operlog.setTakeTime(endTime - startTime); // 设置执行时间
            // 设置返回结果
            operlog.setResponseResult(JSON.toJSONString(result));
            // 插入数据库
            operLogService.add(operlog);
        }
        return result;
    }
    @AfterReturning(value = "loginLogPointCut()", returning = "result")
    public void saveLoginLog(JoinPoint joinPoint, Object result) {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog operlog = new OperLog();

            operlog.setTitle("用户模块");//设置模块名称
            operlog.setContent("用户登录");//设置日志内容

            // 将入参转换成json
            String params = argsArrayToString(joinPoint.getArgs());
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName + "()";
            operlog.setMethod(methodName); //设置请求方法
            operlog.setRequestMethod(request.getMethod());//设置请求方式
            operlog.setRequestParam(params); // 请求参数
            operlog.setResponseResult(JSON.toJSONString(result)); // 返回结果

            // 获取用户名（params中解析)
            JSONObject jsonObject = JSON.parseObject(params);
            // 从 JSONObject 中获取用户名
            String userName = jsonObject.getString("userName");
            operlog.setOperName(userName);

            operlog.setIp(getIp(request)); // IP地址
            operlog.setRequestUrl(request.getRequestURI()); // 请求URI
            operlog.setOperTime(new Date()); // 时间
            operlog.setStatus(0);//操作状态（0正常 1异常）  // 这里只有正常的状态...
            Long takeTime = 0L;
            operlog.setTakeTime(takeTime);  // 没有用到
            //插入数据库
            operLogService.add(operlog);
        } catch (Exception e) {
            System.out.println("================================");
            e.printStackTrace();
        }
    }



    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o != null) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.trim();
    }


    //根据HttpServletRequest获取访问者的IP地址
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}