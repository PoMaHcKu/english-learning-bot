package com.rom.english.learnbot.bot.handler;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.User;

public interface UserHandler<T extends BotApiObject> {

    User getUser(T apiObject);

}
