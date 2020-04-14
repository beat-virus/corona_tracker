package de.govhackathon.wvsvcoronatracker.services;

import de.govhackathon.wvsvcoronatracker.model.Contact;
import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.repositories.ContactRepository;
import de.govhackathon.wvsvcoronatracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.UUID;

import static de.govhackathon.wvsvcoronatracker.utils.TestDataHelper.createTestUser;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class Spec_UsersService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Nested
    class Users {

        @Test
        void should_add_new_user() throws Exception {
            User savedUser = usersService.createUser(createTestUser());
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getToken()).isNotNull();
        }
    }

    @Nested
    class Friends {

        @Test
        void should_add_new_user_with_friends() throws Exception {
            User savedUser = usersService.createUser(createTestUser(
                    Collections.singleton(
                            Contact.builder().phoneHash(UUID.randomUUID().toString()).build()
                    )
            ));
            Contact friend = contactRepository.findByPhoneHash(savedUser.getFriends().iterator().next().getPhoneHash());
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getToken()).isNotNull();
            assertThat(savedUser.getFriends()).isNotNull();
            assertThat(friend).isNotNull();
        }

        @Test
        void should_update_user_with_friends() throws Exception {
            User savedUser = usersService.createUser(createTestUser());
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getToken()).isNotNull();
            assertThat(savedUser.getFriends()).isNull();
            // update
            savedUser.setFriends(Collections.singleton(
                    Contact.builder()
                            .phoneHash(UUID.randomUUID().toString())
                            .build()
            ));
            savedUser = usersService.updateUser(savedUser);
            Contact friend = contactRepository.findByPhoneHash(savedUser.getFriends().iterator().next().getPhoneHash());
            assertThat(savedUser).isNotNull();
            assertThat(savedUser.getToken()).isNotNull();
            assertThat(friend).isNotNull();
        }
    }

}