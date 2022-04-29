package com.rom.english.learnbot.bot.handler;

import com.rom.english.learnbot.bot.keyboard.CallbackNames;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import com.rom.english.learnbot.mapper.TelegramUserToUserMapper;
import com.rom.english.learnbot.model.UserPref;
import com.rom.english.learnbot.model.WordRelationGroup;
import com.rom.english.learnbot.service.UserPrefService;
import com.rom.english.protobuf.CallbackProto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@RequiredArgsConstructor
public class CallbackHandler {

    private final ReplyKeyboardCreator keyboardCreator;
    private final CallbackQueryDataWrapper callbackQueryWrapper;
    private final UserHandler<CallbackQuery> userHandler;
    private final TelegramUserToUserMapper userMapper;
    private final UserPrefService userPrefService;


    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();
        CallbackProto.Payload callback = callbackQueryWrapper.decode(data);

        if (callback.getCommand().equals(CallbackNames.CHECK_ANSWER.getTitle())) {
            return checkAndSendAnswer(chatId, callback);
        }

        if (callback.getCommand().equals(CallbackNames.SET_GROUP.getTitle())) {
            User user = userHandler.getUser(callbackQuery);
            return setUserGroup(chatId, callback, user);
        }

        return null;
    }

    private BotApiMethod<?> checkAndSendAnswer(String chatId, CallbackProto.Payload data) {
        boolean isRight = data.getRightAnswerId() == data.getSelectedAnswerId();
        String message = isRight ? "It's right, great!!!" : "It's incorrect, right answer - <b>" + data.getRightAnswer() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }

    private BotApiMethod<?> setUserGroup(String chatId, CallbackProto.Payload callback, User user) {
        UserPref prefs = userPrefService.getByUserOrCreate(userMapper.toEntityUser(user));
        long newGroupId = callback.getNewGroupId();
        userPrefService.setSelectedWordGroup(prefs, WordRelationGroup.builder().id(newGroupId).build());

        SendMessage sendMessage = new SendMessage(chatId, "Group is updated");
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }
}
