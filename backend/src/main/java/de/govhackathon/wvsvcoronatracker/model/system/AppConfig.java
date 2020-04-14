package de.govhackathon.wvsvcoronatracker.model.system;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "APP_CONFIG")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {

    // FOREIGN settings, e.g. FCM
    public static final String FCM_KEY = "fcm.apikey";

    // OWN settings, prefixed with ct.*
    public static final String API_KEY = "ct.apikey";
    public static final String API_SECRET= "ct.apiSecret";

    public static final String API_PATH = "/api/v1";

    public static final ZoneOffset TIMEZONE = ZoneOffset.UTC;

    @Id
    private String key;

    @NotNull
    private String value;
}
