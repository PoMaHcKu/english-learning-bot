package com.rom.english.learnbot.config;

import com.rom.english.learnbot.bot.CallbackHandler;
import com.rom.english.learnbot.bot.EnglishLearnTelegramBot;
import com.rom.english.learnbot.bot.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotConfig {

    private String webhookPath;
    private String botName;
    private String botToken;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(webhookPath).build();
    }

    @Bean
    public EnglishLearnTelegramBot springWebhookBot(SetWebhook setWebhook,
                                                    MessageHandler messageHandler,
                                                    CallbackHandler callbackHandler) {
        EnglishLearnTelegramBot bot = new EnglishLearnTelegramBot(setWebhook, messageHandler, callbackHandler);
        bot.setBotPath(webhookPath);
        bot.setBotUsername(botName);
        bot.setBotToken(botToken);
        return bot;
    }

}