package cn.dmego.seata.at.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: OrderServiceApplication
 *
 * @description: OrderServiceApplication
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:42
 **/
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
