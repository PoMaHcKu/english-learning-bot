package com.rom.english.learnbot.bot.keyboard;

import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.Word;
import com.rom.english.protobuf.CallbackProto;
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

    /**
     * @return keyboard, that permanently shows under the text input
     */
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNames.ONE_MORE_QUESTION.getTitle()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    /**
     * @return keyboard with answers for a question
     */
    public InlineKeyboardMarkup getQuestionKeyboard(Question question) {
        Set<Word> answerWords = question.getAnswerWords();

        List<List<InlineKeyboardButton>> rows = answerWords.stream()
                .map(w -> getButton(w, question.getQuestionWord()))
                .collect(Collectors.toList());
        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    /**
     * @param word using for creating button and callback data
     * @param rightAnswer using for creating callback data
     * @return row of buttons
     */
    private List<InlineKeyboardButton> getButton(Word word, Word rightAnswer) {

        CallbackProto.Callback callback = CallbackProto.Callback.newBuilder()
                .setCommand(CallbackNames.CHECK_ANSWER.getTitle())
                .setRightAnswer(rightAnswer.getWord())
                .setSelectedAnswerId(word.getId())
                .setRightAnswerId(rightAnswer.getId())
                .build();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(word.getWord());
        button.setCallbackData(callback.toString());

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
