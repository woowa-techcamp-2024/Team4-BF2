package woowa.team4.bff.common.aop;


import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MethodLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodLoggingAspect.class);

    @Around("@annotation(MethodLogging)")
    public Object methodLoggingExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        logger.info("Method '{}' called with parameters: {}", methodName,
                Arrays.toString(combineNamesAndValues(parameterNames, args)));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        logger.info("Method '{}' execution time: {} ms", methodName, stopWatch.getTotalTimeMillis());

        return result;
    }

    private String[] combineNamesAndValues(String[] names, Object[] values) {
        String[] combined = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            combined[i] = names[i] + "=" + values[i];
        }
        return combined;
    }
}