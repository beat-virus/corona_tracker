package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.repositories.AppConfigRepository;
import de.govhackathon.wvsvcoronatracker.services.PushService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service("pushService")
public class PushServiceImpl implements PushService {

    private static final Logger LOG = LoggerFactory.getLogger(PushServiceImpl.class);

    private AppConfigRepository configRepository;

    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

    private String fcmApiKey;

    @Autowired
    public PushServiceImpl(AppConfigRepository configRepository) {
        this.configRepository = configRepository;
        Optional<AppConfig> fcmKey = this.configRepository.findById(AppConfig.FCM_KEY);
        if (fcmKey.isPresent()) {
            this.fcmApiKey = fcmKey.get().getValue();
        } else {
            LOG.error("No FCM API Key given");
        }
    }

    @Override
    public void sendPushToDevice(String title, String msg, String deviceToken) {
        this.sendFirebaseNotification(this.fcmApiKey, title, msg, deviceToken, "");
    }

    @Override
    public void sendPushToDevice(String title, String msg, String deviceToken, Map<String, String> data) {
        this.sendFirebaseNotification(this.fcmApiKey, title, msg, deviceToken, "", data);
    }


    private void sendFirebaseNotification(String apiKey, String title, String msg, String deviceToken, String typeId) {
        this.sendFirebaseNotification(apiKey, title, msg, deviceToken, typeId, new HashMap<>());
    }

    private void sendFirebaseNotification(String apiKey, String title, String msg, String deviceToken, String typeId, Map<String, String> data) {
        LOG.trace("Starting send android push message to device with token {}", deviceToken);

        // Prepare JSON containing the GCM message content. What to send and where to send.
        final GoogleCloudMessage message = new GoogleCloudMessage(deviceToken,
                new GoogleCloudMessageData(title, msg, "icon", "icon", "icon", typeId, data));

        // setup request to GCM
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "key=" + apiKey);
        HttpEntity<GoogleCloudMessage> entity = new HttpEntity<>(message, headers);

        // sent request
        restTemplate.exchange(FCM_URL, HttpMethod.POST, entity, Void.class);

        LOG.trace("Done sending push message to device with token");
    }
}

@Data
@AllArgsConstructor
class GoogleCloudMessage implements Serializable {

    private String to;

    private GoogleCloudMessageData data;

}

@Data
@AllArgsConstructor
class GoogleCloudMessageData implements Serializable {

    private String title;

    private String message;

    private String image;

    private String smallIcon;

    private String largeIcon;

    private String typeId;

    private Map<String, String> data;

}
