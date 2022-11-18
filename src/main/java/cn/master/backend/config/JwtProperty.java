package cn.master.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author create by 11's papa on 2022-11-18
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    private String authorization;
    private String secretKey;
    private String tokenPrefix;
    private long expirationTime;
}
