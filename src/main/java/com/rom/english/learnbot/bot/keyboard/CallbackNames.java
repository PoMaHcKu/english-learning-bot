package com.rom.english.learnbot.bot.keyboard;

/**
 * It is used for handling by {@link com.rom.english.learnbot.bot.CallbackHandler}
 */
public enum CallbackNames {
    CHECK_ANSWER("CA");

    private final String title;

    CallbackNames(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
