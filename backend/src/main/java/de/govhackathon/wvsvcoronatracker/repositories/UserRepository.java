package de.govhackathon.wvsvcoronatracker.repositories;

import org.springframework.data.repository.CrudRepository;

import de.govhackathon.wvsvcoronatracker.model.User;

public interface UserRepository extends CrudRepository<User, String> {

    User findByContactDetailsPhoneHash(String phoneHash);

    User findByToken(String token);

}
