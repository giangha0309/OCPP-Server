//package eu.chargetime.ocpp.jsonserverimplementation.config;
//
//import eu.chargetime.ocpp.JSONClient;
//import eu.chargetime.ocpp.JSONServer;
//import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
//import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//public class JsonServerConfig {
//
//    @Bean
//    public JSONClient jsonClient(ClientCoreProfile core) {
//        return new JSONClient(core);
//    }
//
//    @Bean
//    public JSONServer jsonServer(ServerCoreProfile core) {
//        return new JSONServer(core);
//    }
//
//}
