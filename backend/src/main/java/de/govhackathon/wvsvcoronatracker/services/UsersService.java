package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsersService {

    /**
     * Get all users
     *
     * @return List of all users, empty collection if none available
     */
    List<User> getUsers();

    /**
     * Get a user by id
     *
     * @return users or null
     */
    Optional<User> getUser(String id);

    /**
     * Create a user
     *
     * @param user data to create user from
     * @return Created user
     */
    User createUser(final User user);

    /**
     * Returns users by their phone hashes
     * @param list of phone hashes
     * @return set of users
     */
    Set<User> getUsersByPhoneHashes(List<String> collect);

  /**
   * Update a user
   *
   * @param user user data to be changed
   * @return Updated user
   */
  User updateUser(final User user);

  /**
   * Delete a user
   * @param user user data to be deleted
   */
  void deleteUser(final User user);
}
