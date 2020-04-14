package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.Contact;
import de.govhackathon.wvsvcoronatracker.model.MedicalState;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import de.govhackathon.wvsvcoronatracker.services.FriendsNotifyService;
import de.govhackathon.wvsvcoronatracker.services.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("friendsNotifyService")
public class FriendsNotifyServiceImpl implements FriendsNotifyService {

    private static final Logger LOG = LoggerFactory.getLogger(FriendsNotifyServiceImpl.class);

    private static final Integer MAX_DEGREES = 2;

    private static final String TITLE = "Meldung in deinem Freundeskreis"; // TODO: text/i18n

    private PushService pushService;

    private UserRepository userRepository;

    @Autowired
    public FriendsNotifyServiceImpl(PushService pushService, UserRepository userRepository) {
        this.pushService = pushService;
        this.userRepository = userRepository;
    }

    public void onMedicalStateChanged(User user, MedicalState state) {
        switch (state) {
            case INFECTED:
            case CURED:
                this.iterateFriends(user, state);
        }
    }

    private void iterateFriends(User user, MedicalState state) {
        Set<Contact> friends = user.getFriends();

        for (Contact friend : friends) {
            User friendUser = userRepository.findByContactDetailsPhoneHash(friend.getPhoneHash());
            if (friendUser != null) {
                this.pushService.sendPushToDevice(this.TITLE, String.format("Dein Freund {NAME} ist %s", state), friendUser.getToken(), this.buildData(user.getToken()));
                this.iterateFriendsRecursion(user, state, friendUser, 1);
            } else {
                LOG.info("No user for contact {}", friend.getId());
            }
        }

        for (Contact friend : friends) {
        }
    }

    private void iterateFriendsRecursion(User user, MedicalState state, User friend, Integer degree) {
        Set<Contact> friends = user.getFriends();

        for (Contact friendContact : friends) {
            User friendUser = userRepository.findByContactDetailsPhoneHash(friendContact.getPhoneHash());
            if (friendUser != null) {
                if (degree < 2) {
                    this.pushService.sendPushToDevice(
                            this.TITLE,
                            String.format("Ein Freund  ist %s", state),
                            friendUser.getToken(),
                            this.buildData(friend.getToken()));
                } else {
                    this.pushService.sendPushToDevice(
                            this.TITLE,
                            String.format("Ein Freund %d. Grades ist %s", degree, state),
                            friendUser.getToken(),
                            this.buildData(friend.getToken()));
                }
                this.iterateFriendsRecursion(user, state, friendUser, degree + 1);
            } else {
                LOG.info("No user for contact {}", friendContact.getId());
            }
        }
    }

    private Map<String, String> buildData(String token) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("friendToken", token);
        return map;
    }
}
