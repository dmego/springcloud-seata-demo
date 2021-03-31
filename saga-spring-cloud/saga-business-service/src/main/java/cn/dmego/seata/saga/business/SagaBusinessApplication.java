package cn.dmego.seata.saga.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SagaBusinessApplication
 *
 * @author dmego
 * @date 2021/3/31 10:48
 */
@EnableFeignClients
@SpringBootApplication
public class SagaBusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(SagaBusinessApplication.class, args);
    }
}
