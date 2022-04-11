package com.rom.english.learnbot.bot.keyboard;

import com.rom.english.learnbot.bot.BotCommands;
import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.Word;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReplyKeyboardCreator {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.ONE_MORE_QUESTION.getTitle()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getQuestionKeyboard(Question question) {
        Set<Word> answerWords = question.getAnswerWords();

        List<List<InlineKeyboardButton>> rows = answerWords.stream()
                .map(w -> getButton(w, question))
                .collect(Collectors.toList());
        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(Word word, Question question) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(word.getWord());
        JSONObject jsonQuestion = new JSONObject();
        jsonQuestion.put("selectedId", word.getId());
        jsonQuestion.put("right", question.getRightAnswerId());
        String callbackData = BotCommands.CHECK_ANSWER.getTitle() + "_pl:" + jsonQuestion.toString();
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
