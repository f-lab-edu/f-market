package com.sorryisme.fmarket.aop;


import com.sorryisme.fmarket.annotation.IdempotencyKeyParam;
import com.sorryisme.fmarket.exception.DuplicateDataException;
import com.sorryisme.fmarket.mapper.IdempotencyKeyMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {

    private final IdempotencyKeyMapper idempotencyKeyMapper;

    @Around("@annotation(com.sorryisme.fmarket.annotation.Idempotent)")
    public Object handleIdempotency(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        Object[] args = joinPoint.getArgs();
        String idempotencyKey = null;

        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < paramAnnotations.length; i++) {
            for (Annotation annotation : paramAnnotations[i]) {
                if (annotation instanceof IdempotencyKeyParam) {
                    Object arg = args[i];

                    if (!(arg instanceof String)) {
                        throw new IllegalArgumentException("Idempotency-Key 파라미터는 String 타입이어야 합니다.");
                    }

                    idempotencyKey = (String) arg;
                    break;
                }
            }

            if (idempotencyKey != null) {
                break;
            }
        }

        if (idempotencyKey == null)  throw new IllegalArgumentException("Idempotency-Key 헤더가 필요합니다.");
        if (idempotencyKey.length() != 36) throw new IllegalArgumentException("idempotencyKey 키는 36자리여야 합니다.");

        int result = idempotencyKeyMapper.insertIgnoreIdempotencyKey(idempotencyKey);
        if (result <= 0) throw new DuplicateDataException("중복된 요청입니다.");


        return joinPoint.proceed();
    }
}
