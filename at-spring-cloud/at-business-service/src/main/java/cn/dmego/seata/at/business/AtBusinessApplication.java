package cn.dmego.seata.at.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: AtBusinessApplication
 *
 * @description: AtBusinessApplication
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:41
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class AtBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtBusinessApplication.class, args);
    }
}
