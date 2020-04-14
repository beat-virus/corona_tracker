package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;

public interface FriendsNotifyService {
    /**
     * Notify friends and friends of friends
     * if medical state of a user has changed.
     */
    void onMedicalStateChanged(User user, MedicalState state);
}
