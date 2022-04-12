package com.rom.english.learnbot.bot.handler;

import com.google.protobuf.TextFormat;
import com.rom.english.learnbot.bot.keyboard.CallbackNames;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import com.rom.english.protobuf.CallbackProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Slf4j
@RequiredArgsConstructor
public class CallbackHandler {

    private final ReplyKeyboardCreator keyboardCreator;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();

        if (data.startsWith(CallbackNames.CHECK_ANSWER.getTitle())) {
            return checkAndSendAnswer(chatId, data);
        }
        return null;
    }

    private BotApiMethod<?> checkAndSendAnswer(String chatId, String data) {
        CallbackProto.Callback payload = getPayload(data);
        boolean isRight = payload.getRightAnswerId() == payload.getSelectedAnswerId();
        String message = isRight ? "It's right, great!!!" : "It's incorrect, right answer is " + payload.getRightAnswer();
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }

    private CallbackProto.Callback getPayload(String data) {
        try {
         return TextFormat.parse(data, CallbackProto.Callback.class);
        } catch (TextFormat.ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
