package com.rom.english.learnbot.mapper;

import com.rom.english.learnbot.model.User;
import org.springframework.stereotype.Component;

@Component
public class TelegramUserToUserMapper {

    public User toEntityUser(org.telegram.telegrambots.meta.api.objects.User tUser) {
        return User.builder()
                .id(tUser.getId())
                .firstName(tUser.getFirstName())
                .lastName(tUser.getLastName())
                .isBot(tUser.getIsBot())
                .build();
    }
}
