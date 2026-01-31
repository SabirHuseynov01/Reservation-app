package com.example.reservation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private Jwt jwt = new Jwt();
    private Mail mail = new Mail();

    @Data
    public static class Jwt {
        private String secret;
        private long expirationMsec = 864_000_000;
        private String header = "Authorization";
        private String prefix = "Bearer ";
    }

    @Data
    public static class Mail {
        private String host;
        private int port;
        private String username;
        private String password;
    }
}
