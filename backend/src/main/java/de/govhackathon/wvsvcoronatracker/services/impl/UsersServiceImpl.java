package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.Contact;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.ContactRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import de.govhackathon.wvsvcoronatracker.services.UsersService;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<User> getUsers() {
        return IterableUtils.toList(userRepository.findAll());
    }

    @Override
    public Optional<User> getUser(final String id) {
        return Optional.of(userRepository.findByToken(id));
    }

    @Override
    public User createUser(final User user) {
        return this.updateUser(user);
    }

    @Override
    public Set<User> getUsersByPhoneHashes(List<String> friendsPhoneHashes) {

        Set<User> users = new HashSet<>();
        //TODO find better, DB-based solution
        friendsPhoneHashes.forEach(f -> {
            User byPhoneHash = userRepository.findByContactDetailsPhoneHash(f);
            if (byPhoneHash != null) {
                users.add(byPhoneHash);
            }

        });

        return users;
    }

    @Override
    public User updateUser(final User user) {
        // TODO move to mapper
        if (user.getContactDetails() != null) {
            Contact existingContact = this.contactRepository.findByPhoneHash(user.getContactDetails().getPhoneHash());
            if (existingContact != null) {
                user.setContactDetails(existingContact);
            } else {
                user.setContactDetails(this.contactRepository.save(user.getContactDetails()));
            }
        }
        if (user.getFriends() != null) {
            user.getFriends().forEach(friend -> this.contactRepository.save(friend));
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }
}
