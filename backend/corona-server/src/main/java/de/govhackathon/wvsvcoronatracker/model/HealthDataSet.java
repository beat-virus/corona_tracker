package de.govhackathon.wvsvcoronatracker.model;

import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

//@Data //do not use this: causes stackoverflow due to circular dependency

@EqualsAndHashCode(callSuper = false)
@Builder
@Getter
@Setter
@Entity
@Table(name = "\"HEALTH_DATASET\"")
@AllArgsConstructor
@NoArgsConstructor
public class HealthDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    private OffsetDateTime creationDate = OffsetDateTime.now(AppConfig.TIMEZONE);

    private MedicalState medicalState = MedicalState.UNKNOWN;

    //private GeoFence geoFence;

    //@OneToOne
    //private Position position;
}
