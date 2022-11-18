package cn.master.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author the2n
 */
@SpringBootApplication
@MapperScan("cn.master.backend.mapper")
public class AnduinBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnduinBackendApplication.class, args);
    }

}
