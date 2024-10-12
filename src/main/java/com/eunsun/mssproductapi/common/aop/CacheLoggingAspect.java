package com.eunsun.mssproductapi.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class CacheLoggingAspect {

    @AfterReturning(pointcut = "@annotation(cacheable)", returning = "result")
    public void logCacheableResult(Cacheable cacheable, Object result) {
        log.info("==Cacheable method returned: {}, Caches: {}", result, cacheable.value());
    }

    @AfterReturning("@annotation(org.springframework.cache.annotation.CacheEvict) || @annotation(org.springframework.cache.annotation.Caching)")
    public void sendRefreshRequestAfterCacheEvict(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Caching caching = method.getAnnotation(Caching.class);
        if (caching != null) {
            for (CacheEvict evict : caching.evict()) {
                log.info("==CacheEvict inside Caching called. Caches: {}, Key: {}, AllEntries: {}",
                        Arrays.toString(evict.value()),
                        evict.key(),
                        evict.allEntries());
            }
        }
    }
}
