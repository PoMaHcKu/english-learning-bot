package com.rom.english.learnbot.bot.handler;

import com.rom.english.learnbot.bot.keyboard.ButtonNames;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.mapper.UserServiceWrapper;
import com.rom.english.learnbot.model.User;
import com.rom.english.learnbot.model.UserPref;
import com.rom.english.learnbot.model.WordReference;
import com.rom.english.learnbot.model.WordRelationGroup;
import com.rom.english.learnbot.service.QuestionService;
import com.rom.english.learnbot.service.UserPrefService;
import com.rom.english.learnbot.service.WordRelationGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final QuestionService questionService;
    private final ReplyKeyboardCreator keyboardCreator;
    private final UserHandler<Message> userHandler;
    private final UserServiceWrapper userServiceWrapper;
    private final UserPrefService userPrefService;
    private final WordRelationGroupService relationGroupService;


    public BotApiMethod<?> answerMessage(Message message) {
        String text = message.getText();
        String chatId = message.getChatId().toString();

        if (text.equals(ButtonNames.ONE_MORE_QUESTION.getTitle())) {
            User user = userServiceWrapper.getOrCreate(userHandler.getUser(message));
            UserPref pref = userPrefService.getByUserOrCreate(user);
            Question question;
            if (pref.getSelectedGroup() != null) {
                WordRelationGroup selectedGroup = pref.getSelectedGroup();
                question = questionService.getRandomQuestionFromGroup(selectedGroup.getId());
            } else {
                question = questionService.getRandomQuestion();
            }
            SendMessage sendMessage = new SendMessage(chatId, question.getQuestionWord().getWord());
            sendMessage.setReplyMarkup(keyboardCreator.getQuestionKeyboard(question));
            return sendMessage;
        }

        if (text.equals(ButtonNames.CONFIG.getTitle())) {
            SendMessage sendMessage = new SendMessage(chatId, "Config menu");
            sendMessage.setReplyMarkup(keyboardCreator.getConfigKeyboard());
            return sendMessage;
        }

        if (text.equals(ButtonNames.RETURN_TO_MAIN_PAGE.getTitle())) {
            SendMessage sendMessage = new SendMessage(chatId, "Main menu");
            sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
            return sendMessage;
        }

        if (text.startsWith(ButtonNames.SELECT_GROUPS.getTitle())) {
            String prefixAndPage = text.substring(ButtonNames.SELECT_GROUPS.getTitle().length());
            int pageNum = 0;
            if (prefixAndPage.startsWith("_")) {
                pageNum = Integer.parseInt(prefixAndPage.substring(1));
            }
            User user = userServiceWrapper.getOrCreate(userHandler.getUser(message));
            UserPref prefs = userPrefService.getByUserOrCreate(user);

            Page<WordRelationGroup> page = relationGroupService.getPage(pageNum);

            SendMessage sendMessage = new SendMessage(chatId, "Select group");
            sendMessage.setReplyMarkup(keyboardCreator.getGroupSelectionKeyboard(prefs, page));
            return sendMessage;
        }

        SendMessage sendMessage = new SendMessage(chatId, "Do you want a question?");
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }
}
