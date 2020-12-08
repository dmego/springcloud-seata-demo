package cn.dmego.seata.tcc.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: TCCBusinessApplication
 *
 * @description: 业务服务
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/6 17:26
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class TCCBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCBusinessApplication.class, args);
    }
}
