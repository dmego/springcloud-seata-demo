package cn.dmego.seata.tcc.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: TCCOrderApplication
 *
 * @description: 订单服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:28
 **/
@SpringBootApplication
@EnableFeignClients
public class TCCOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCOrderApplication.class, args);
    }
}
