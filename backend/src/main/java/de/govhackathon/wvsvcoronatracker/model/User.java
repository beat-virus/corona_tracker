package de.govhackathon.wvsvcoronatracker.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@Entity
// quote user, see https://stackoverflow.com/questions/3608420/hibernate-saving-user-model-to-postgres
@Table(name = "\"USERS\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotNull
    @Id
    @Column(name = "USER_ID")
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    private Contact contactDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<HealthDataSet> healthDataSetList = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FRIENDS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "FRIEND_ID")
    )
    @EqualsAndHashCode.Exclude
    private Set<Contact> friends;

}
