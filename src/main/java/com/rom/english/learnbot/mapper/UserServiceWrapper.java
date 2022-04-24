package com.rom.english.learnbot.mapper;

import com.rom.english.learnbot.model.User;
import com.rom.english.learnbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceWrapper {

    private final UserService userService;
    private final TelegramUserToUserMapper userMapper;

    public User getOrCreate(org.telegram.telegrambots.meta.api.objects.User tUser) {
        return userService.getOrCreate(userMapper.toEntityUser(tUser));
    }

}
