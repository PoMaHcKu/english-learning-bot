package com.rom.english.learnbot.bot.keyboard;

import com.rom.english.learnbot.bot.handler.CallbackHandler;

/**
 * It is used for handling by {@link CallbackHandler}
 */
public enum CallbackNames {
    CHECK_ANSWER("CA"),
    SET_GROUP("SG");

    private final String title;

    CallbackNames(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
