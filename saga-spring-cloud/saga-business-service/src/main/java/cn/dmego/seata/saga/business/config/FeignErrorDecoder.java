package cn.dmego.seata.saga.business.config;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author dmego
 * @date 2021/05/10 18:36
 */
@Configuration
public class FeignErrorDecoder extends ErrorDecoder.Default {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);
        try {
            // 将 FeignException 包装成 普通异常
            if (exception instanceof FeignException) {
                exception = new RuntimeException(exception.getMessage());
            }
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return exception;
    }
}
