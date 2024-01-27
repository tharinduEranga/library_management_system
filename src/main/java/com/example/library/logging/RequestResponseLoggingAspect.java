package com.example.library.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestResponseLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);

    @Around("@annotation(com.example.library.logging.Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Request API method {}, body: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        final var result = joinPoint.proceed();
        if (result instanceof ResponseEntity<?> responseEntity) {
            logResponse(responseEntity);
        }
        return result;
    }

    private void logResponse(final ResponseEntity<?> responseEntity) {
        if (responseEntity.getBody() instanceof PageImpl<?>) {
            // Convert the collection to a JSON string or any other log-friendly format
            String responseBodyJson = convertCollectionToJson((PageImpl<?>) responseEntity.getBody());
            logger.info("Response body: {}", responseBodyJson);
        } else {
            logger.info("Response body: {}", responseEntity.getBody());
        }
    }

    private String convertCollectionToJson(PageImpl<?> collection) {
        try {
            final var objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(collection);
        } catch (Exception e) {
            return "Error converting collection to JSON";
        }
    }
}
