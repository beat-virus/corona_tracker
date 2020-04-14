package de.govhackathon.wvsvcoronatracker.model;

import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HEALTH_DATASETS")
public class HealthDataSet {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @EqualsAndHashCode.Exclude
    private User user;

    @Builder.Default
    private OffsetDateTime creationDate = OffsetDateTime.now(AppConfig.TIMEZONE);

    private MedicalState medicalState = MedicalState.UNKNOWN;

}
