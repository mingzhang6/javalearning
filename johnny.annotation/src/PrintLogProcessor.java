import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

@Aspect
@Component
@Slf4j
public class PrintLogProcessor {

    @Around("@annotation(printInvokeLog)")
    public Object around(ProceedingJoinPoint point, PrintInvokeLog printInvokeLog) throws Throwable {
        // 获取目标class对象
        Class<?> aClass = point.getTarget().getClass();
        // 获取class名称
        String className = aClass.getName();
        // 获取方法名称
        String methodName = point.getSignature().getName();
        // 获取方法所有参数
        Object[] args = point.getArgs();
        // 获取所有参数名称
        String[] parameterNames = ((CodeSignature) point.getSignature()).getParameterNames();
        StringBuilder stringBuilder = new StringBuilder();
        // 遍历参数列表，将参数名称与参数值对应
        for (int i = 0; i < args.length; i++) {
            String parameterName = parameterNames[i];
            stringBuilder.append(parameterName).append("=").append(args[i]);
        }
        // 方法调用前打印日志
        log.info("LOG, 类名:{}, 方法名:{}, 方法参数:{}", className, methodName,  stringBuilder.toString());
        long startTime = System.currentTimeMillis();
        // 调用方法
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis();
        // 方法调用后打印日志
        log.info("LOG, , 方法返回值:{}, 方法耗时:{}ms", proceed, (endTime - startTime));
        return proceed;
    }

}
