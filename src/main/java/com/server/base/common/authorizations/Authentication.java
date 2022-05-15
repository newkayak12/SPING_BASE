package com.server.base.common.authorizations;

import com.server.base.common.constants.Constants;
import com.server.base.common.exception.Exceptions;
import com.server.base.common.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
@Aspect
public class Authentication {
    TokenManager tokenManager = new TokenManager();

    @Before("@annotation(com.server.base.common.authorizations.Authorization)")
    public void decrypt(JoinPoint joinPoint) throws ServiceException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] parameterValues = joinPoint.getArgs();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        Reflections reflections = new Reflections("com.server.base.repository.dto");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(AuthorizeDto.class);
        Iterator<Class<?>> targetOne = classSet.iterator();
        Class<?> target = targetOne.next();
        System.out.println(target);
        for(int i = 0; i<parameters.length; i++){
            Long isExist = Arrays.asList(parameters[i].getDeclaredAnnotations())
                    .stream().filter(item-> item.annotationType().equals("org.springframework.web.bind.annotation.RequestHeader"))
                    .count();
            if(isExist>0){
                Object  token = parameterValues[i];
                if(Objects.isNull(token)){
                    throw new ServiceException(Exceptions.INVALID_ACCESS);
                }
                parameterValues[i] = tokenManager.decrypt(target.getConstructor().newInstance(), (String) token);
                break;
            }
        }
    }

}
