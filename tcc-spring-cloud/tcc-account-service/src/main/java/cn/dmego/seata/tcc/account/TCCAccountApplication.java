package cn.dmego.seata.tcc.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: TCCAccountApplication
 *
 * @description: 账户服务启动类
 * @author: ZengKai<dmeago@gmail.com>
 * @date: 2020/12/5 17:23
 **/
@SpringBootApplication
public class TCCAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(TCCAccountApplication.class, args);
    }
}
