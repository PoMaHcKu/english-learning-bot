package com.rom.english.learnbot.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class UserCallbackHandler implements UserHandler<CallbackQuery> {

    @Override
    public User getUser(CallbackQuery apiObject) {
        return apiObject.getFrom();
    }
}
