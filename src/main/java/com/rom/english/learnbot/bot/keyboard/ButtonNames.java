package com.rom.english.learnbot.bot.keyboard;

/**
 * It is used for handling by {@link com.rom.english.learnbot.bot.MessageHandler}
 */
public enum ButtonNames {

    ONE_MORE_QUESTION("One more question");

    private final String title;

    ButtonNames(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
