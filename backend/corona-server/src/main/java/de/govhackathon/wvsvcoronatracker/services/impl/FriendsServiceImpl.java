package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import de.govhackathon.wvsvcoronatracker.services.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addFriendsForUser(User user, Set<User> friends) {

        //TODO find a better solution
        friends.stream()
                //you may be friends with yourself, but not here
                .filter(f -> f != user)
                .forEach(
                        f -> f.getUsers().add(user)
        );

        userRepository.saveAll(friends);
    }

    @Override
    public Set<User> getUsersFriends(User user) {

        return userRepository.findByUsers(user);
    }

    @Override
    public void deleteUsersFriends(User user) {

        Set<User> usersFriends = userRepository.findByUsers(user);

        //TODO find a better way
        usersFriends.forEach(f -> {
            user.getFriends().remove(f);

            f.getFriends().remove(user);
        });

        userRepository.save(user);
    }
}
