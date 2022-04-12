package com.rom.english.learnbot.bot;

import com.rom.english.learnbot.bot.keyboard.CallbackNames;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
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
        JSONObject payload = getPayload(data);
        boolean isRight = payload.get("selectedId").equals(payload.get("right"));
        String message = isRight ? "It's right, great!!!" : "It's incorrect, another time...";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }

    private JSONObject getPayload(String data) {
        String keyWord = "_pl:";
        int i = data.indexOf(keyWord);
        String jsonStr = data.substring(i + keyWord.length());
        return new JSONObject(jsonStr);
    }
}
