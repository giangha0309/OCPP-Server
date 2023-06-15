package eu.chargetime.ocpp.jsonserverimplementation.server;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.jsonserverimplementation.config.ApplicationConfiguration;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.reservation.CancelReservationConfirmation;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileConfirmation;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@AllArgsConstructor
@RequestMapping(value="/Server")
@ComponentScan
public class JsonServerImpl {

    private final ServerEvents serverEvents;
    private final JSONServer server;
    private final Environment environment;


    private final ApplicationConfiguration applicationConfiguration;
    private String webSocketBaseUrl() {
        return "localhost:9001";
    };


    @Autowired
    private ServerCoreProfile serverCoreProfile;

    @PostConstruct
    public void startServer() throws Exception {
        server.open("localhost", applicationConfiguration.getServerPort(), serverEvents);
    }

    @GetMapping(path = "/ChangeAvailability")
    public ResponseEntity<Object> ChangeAvailability(
            @RequestParam("availabilityType") AvailabilityType availabilityType,
            @RequestParam("connectorId") Integer connectorId
            ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        ChangeAvailabilityRequest changeAvailabilityRequest = serverCoreProfile.createChangeAvailabilityRequest(availabilityType, connectorId);
        ChangeAvailabilityConfirmation changeAvailabilityConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            changeAvailabilityConfirmation = (ChangeAvailabilityConfirmation) server.send(sessionId, changeAvailabilityRequest).toCompletableFuture().get();
            log.info(String.valueOf(changeAvailabilityConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(changeAvailabilityConfirmation, HttpStatus.OK);
    }

    @GetMapping(path = "/CancelReservation")
    public ResponseEntity<Object> CancelReservation(
            @RequestParam(value = "reservationId", required = false) Integer reservationId
    ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        CancelReservationRequest cancelReservationRequest = new CancelReservationRequest();
        cancelReservationRequest.setReservationId(reservationId);
        CancelReservationConfirmation cancelReservationConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            cancelReservationConfirmation = (CancelReservationConfirmation) server.send(sessionId, cancelReservationRequest).toCompletableFuture().get();
            log.info(String.valueOf(cancelReservationConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(cancelReservationConfirmation, HttpStatus.OK);
    }

    @GetMapping(path = "/ChangeConfiguration")
    public ResponseEntity<Object> ChangeConfiguration(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "value", required = false) String value
            ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        ChangeConfigurationRequest changeConfigurationRequest = serverCoreProfile.createChangeConfigurationRequest(key, value);

        ChangeConfigurationConfirmation changeConfigurationConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            changeConfigurationConfirmation = (ChangeConfigurationConfirmation) server.send(sessionId, changeConfigurationRequest).toCompletableFuture().get();
            log.info(String.valueOf(changeConfigurationConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(changeConfigurationConfirmation, HttpStatus.OK);
    }

    @GetMapping(path = "/ClearCache")
    public ResponseEntity<Object> ClearCache(
    ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        ClearCacheRequest clearCacheRequest = serverCoreProfile.createClearCacheRequest();

        ClearCacheConfirmation clearCacheConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            clearCacheConfirmation = (ClearCacheConfirmation) server.send(sessionId, clearCacheRequest).toCompletableFuture().get();
            log.info(String.valueOf(clearCacheConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(clearCacheConfirmation, HttpStatus.OK);
    }

    @GetMapping(path = "/ClearChargingProfile")
    public ResponseEntity<Object> ClearChargingProfile(
            @RequestParam(value = "connectorId", required = false) Integer connectorId,
            @RequestParam(value = "chargingProfilePurpose", required = false) ChargingProfilePurposeType chargingProfilePurpose,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "stackLevel", required = false) Integer stackLevel
            ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        ClearChargingProfileRequest clearCacheRequest = new ClearChargingProfileRequest();
        clearCacheRequest.setChargingProfilePurpose(chargingProfilePurpose);
        clearCacheRequest.setConnectorId(connectorId);
        clearCacheRequest.setId(id);
        clearCacheRequest.setStackLevel(stackLevel);
        ClearChargingProfileConfirmation clearChargingProfileConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            clearChargingProfileConfirmation = (ClearChargingProfileConfirmation) server.send(sessionId, clearCacheRequest).toCompletableFuture().get();
            log.info(String.valueOf(clearChargingProfileConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(clearChargingProfileConfirmation, HttpStatus.OK);
    }

    @GetMapping(path = "/DataTransfer")
    public ResponseEntity<Object> DataTransfer(
            @RequestParam(value = "vendorId") String vendorId,
            @RequestParam(value = "messageId", required = false) String messageId,
            @RequestParam(value = "data", required = false) String data
            ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        DataTransferRequest dataTransferRequest = serverCoreProfile.createDataTransferRequest(vendorId);
        dataTransferRequest.setMessageId(messageId);
        dataTransferRequest.setData(data);

        DataTransferConfirmation dataTransferConfirmation = null;
        try {
            UUID sessionId = UUID.fromString(environment.getProperty("sessionId"));
            dataTransferConfirmation = (DataTransferConfirmation) server.send(sessionId, dataTransferRequest).toCompletableFuture().get();
            log.info(String.valueOf(dataTransferConfirmation));
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        return new ResponseEntity<>(dataTransferConfirmation, HttpStatus.OK);
    }

}
