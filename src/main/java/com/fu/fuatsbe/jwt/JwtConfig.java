package com.fu.fuatsbe.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.jwt")
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private int tokenExpirationAfterDays;
}
