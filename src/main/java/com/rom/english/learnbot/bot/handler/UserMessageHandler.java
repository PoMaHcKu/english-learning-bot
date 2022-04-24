package com.rom.english.learnbot.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class UserMessageHandler implements UserHandler<Message> {

    @Override
    public User getUser(Message message) {
        return message.getFrom();
    }
}
