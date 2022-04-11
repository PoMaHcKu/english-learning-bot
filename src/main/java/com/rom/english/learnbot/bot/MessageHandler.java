package com.rom.english.learnbot.bot;

import com.rom.english.learnbot.bot.keyboard.ButtonNameEnum;
import com.rom.english.learnbot.bot.keyboard.ReplyKeyboardCreator;
import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final QuestionService questionService;
    private final ReplyKeyboardCreator keyboardCreator;

    public BotApiMethod<?> answerMessage(Message message) {
        String text = message.getText();
        String chatId = message.getChatId().toString();

        if (text.equals(ButtonNameEnum.ONE_MORE_QUESTION.getTitle())) {
            Question randomQuestion = questionService.getRandomQuestion();
            SendMessage sendMessage = new SendMessage(chatId, randomQuestion.getQuestionWord().getWord());
            sendMessage.setReplyMarkup(keyboardCreator.getQuestionKeyboard(randomQuestion));
            return sendMessage;
        }

        SendMessage sendMessage = new SendMessage(chatId, "Do you want a question?");
        sendMessage.setReplyMarkup(keyboardCreator.getMainMenuKeyboard());
        return sendMessage;
    }
}
