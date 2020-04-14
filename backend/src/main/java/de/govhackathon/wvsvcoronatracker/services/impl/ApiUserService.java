package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.Role;
import de.govhackathon.wvsvcoronatracker.model.system.AppConfig;
import de.govhackathon.wvsvcoronatracker.repositories.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiUserService implements UserDetailsService {

    @Autowired
    private AppConfigRepository configRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<AppConfig> apiKey = this.configRepository.findById(AppConfig.API_KEY);
        Optional<AppConfig> apiSecret = this.configRepository.findById(AppConfig.API_SECRET);
        if (apiKey.isPresent() && login.equalsIgnoreCase(apiKey.get().getValue())) {
            User.UserBuilder users = User.withDefaultPasswordEncoder();
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            UserDetails user = users
                    .username(apiKey.get().getValue())
                    .password(apiSecret.get().getValue())
                    .roles(Role.APP_USER.name())
                    .build();
            manager.createUser(user);
            return user;
        } else {
            return null;
        }
    }
}
