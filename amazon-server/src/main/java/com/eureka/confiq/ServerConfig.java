package com.eureka.confiq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class ServerConfig {
    private int port;

    // Getter/Setter
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
}
