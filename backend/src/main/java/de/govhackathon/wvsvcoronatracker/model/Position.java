package de.govhackathon.wvsvcoronatracker.model;

import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "POSITIONS")
public class Position {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private String userId;

    @Builder.Default
    private OffsetDateTime timestamp = OffsetDateTime.now(AppConfig.TIMEZONE);

    private String protocol;

    private Boolean outdated;

    private Boolean valid;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private Integer accuracy; // meters

    //TODO add correct jpa annotation
    //private Object network;
}
