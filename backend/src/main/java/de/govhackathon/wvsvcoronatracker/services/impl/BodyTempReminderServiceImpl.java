package de.govhackathon.wvsvcoronatracker.services.impl;

import de.govhackathon.wvsvcoronatracker.model.User;
import de.govhackathon.wvsvcoronatracker.services.BodyTempService;
import de.govhackathon.wvsvcoronatracker.services.PushService;
import de.govhackathon.wvsvcoronatracker.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Send body temperature measurement reminder to users if
 * they didn't already report their temperature recently.
 */
@Component
@Configuration
public class BodyTempReminderServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(BodyTempReminderServiceImpl.class);

    // TODO: i18n
    private static final String TITLE = "Fiebermessen";
    private static final String REMINDER = "Bitte trage deine Körpertemperatur in die App ein";

    @Value("${BODYTEMPREMINDER_ENABLED:false}")
    private boolean enabled;

    private UsersService usersService;
    private BodyTempService bodyTempService;
    private PushService pushService;

    @Autowired
    public BodyTempReminderServiceImpl(UsersService usersService, BodyTempService bodyTempService, PushService pushService) {
        this.usersService = usersService;
        this.bodyTempService = bodyTempService;
        this.pushService = pushService;
    }

    // Run at 08:00 daily
    @Scheduled(cron = "0 0 8 * * *")
    public void morningReminder() {
        if(this.enabled) {
            this.sendReminders(4);
	}
    }

    // Run at 20:00 daily
    @Scheduled(cron = "0 0 20 * * *")
    public void eveningReminder() {
        if(this.enabled) {
            this.sendReminders(16);
	}
    }

    /**
     * Send reminder to all users that have not yet reported
     * their temperature after "afterHour" (0-23) today.
     */
    private void sendReminders(int afterHour) {
        LOG.info("cronjob for reminders after %d o'clock started", afterHour);

        LocalDateTime limit = LocalDate.now().atTime(afterHour, 0);

        for (User user : this.usersService.getUsers()) {
            LocalDateTime last = this.bodyTempService.getLastBodyTempByUser(user.getToken());
            if (last.isBefore(limit)) {
                this.pushService.sendPushToDevice(this.TITLE, this.REMINDER, user.getToken());
            }
        }

        LOG.info("cronjob for reminders after %d o'clock done", afterHour);
    }
}
