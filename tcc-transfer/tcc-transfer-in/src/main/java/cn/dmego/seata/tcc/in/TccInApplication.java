package cn.dmego.seata.tcc.in;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @className: TccInApplication
 *
 * @description: 收钱方启动类
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/8 17:32
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class TccInApplication {

    public static void main(String[] args) {

        SpringApplication.run(TccInApplication.class, args);
    }
}
