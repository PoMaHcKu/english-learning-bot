package com.rom.english.learnbot.bot.keyboard;

public enum ButtonNameEnum {

    ONE_MORE_QUESTION("One more question");

    private final String title;

    ButtonNameEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
