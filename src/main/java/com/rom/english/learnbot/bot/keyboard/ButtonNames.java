package com.rom.english.learnbot.bot.keyboard;

import com.rom.english.learnbot.bot.handler.MessageHandler;

/**
 * It is used for handling by {@link MessageHandler}
 */
public enum ButtonNames {

    ONE_MORE_QUESTION("One more question"),
    SELECT_GROUPS("Select groups"),
    RETURN_TO_MAIN_PAGE("Return to main page"),
    CONFIG("Open config");

    private final String title;

    ButtonNames(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
