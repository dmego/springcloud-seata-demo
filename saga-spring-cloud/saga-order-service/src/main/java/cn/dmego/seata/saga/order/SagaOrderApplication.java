package cn.dmego.seata.saga.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SagaOrderApplication
 *
 * @author dmego
 * @date 2021/3/31 10:52
 */
@EnableFeignClients
@SpringBootApplication
public class SagaOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagaOrderApplication.class, args);
    }

}
