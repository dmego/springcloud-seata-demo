package cn.dmego.seata.tcc.out;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: TccOutApplication
 *
 * @description: 转账业务启动类
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:36
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TccOutApplication {

    public static void main(String[] args) {

        SpringApplication.run(TccOutApplication.class, args);
    }
}
