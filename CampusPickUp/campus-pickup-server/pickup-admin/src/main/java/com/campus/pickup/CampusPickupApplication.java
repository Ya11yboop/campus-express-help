package com.campus.pickup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园快递配送系统 - 启动类
 *
 * @author campus-pickup
 */
@SpringBootApplication
@MapperScan("com.campus.pickup.system.mapper")
public class CampusPickupApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusPickupApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  校园快递配送系统启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
