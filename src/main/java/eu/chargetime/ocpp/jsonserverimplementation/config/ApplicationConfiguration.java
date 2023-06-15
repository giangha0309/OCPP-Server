package eu.chargetime.ocpp.jsonserverimplementation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Getter
public class ApplicationConfiguration {

    @Value("${websocket_server.port}")
    private Integer serverPort;
}
