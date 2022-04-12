package com.rom.english.learnbot.bot.handler;

import com.rom.english.learnbot.bot.keyboard.CallbackNames;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import com.rom.english.protobuf.CallbackProto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class CallbackHandler {

    private final ReplyKeyboardCreator keyboardCreator;
    private final CallbackQueryDataWrapper callbackQueryWrapper;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();
        CallbackProto.Callback callback = callbackQueryWrapper.decode(data);

        if (callback.getCommand().equals(CallbackNames.CHECK_ANSWER.getTitle())) {
            return checkAndSendAnswer(chatId, callback);
        }
        return null;
    }

    private BotApiMethod<?> checkAndSendAnswer(String chatId, CallbackProto.Callback data) {
        boolean isRight = data.getRightAnswerId() == data.getSelectedAnswerId();
        String message = isRight ? "It's right, great!!!" : "It's incorrect, right answer - <b>" + data.getRightAnswer() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }
}
