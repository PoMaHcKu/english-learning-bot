package com.rom.english.learnbot.bot.keyboard;

import com.rom.english.learnbot.bot.handler.CallbackQueryDataWrapper;
import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.UserPref;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordRelationGroup;
import com.rom.english.protobuf.CallbackProto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReplyKeyboardCreator {

    private final CallbackQueryDataWrapper callbackQueryWrapper;

    /**
     * @return keyboard, that permanently shows under the text input
     */
    public ReplyKeyboard getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNames.ONE_MORE_QUESTION.getTitle()));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNames.CONFIG.getTitle()));
        return getKeyboardFromRows(List.of(row1, row2));
    }

    /**
     * @return keyboard with answers for a question
     */
    public ReplyKeyboard getQuestionKeyboard(Question question) {
        Set<Word> answerWords = question.getAnswerWords();

        List<List<InlineKeyboardButton>> rows = answerWords.stream()
                .map(w -> getButton(w, question))
                .collect(Collectors.toList());
        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    /**
     * @param word using for creating button and callback data
     * @param question using for creating callback data
     * @return row of buttons
     */
    private List<InlineKeyboardButton> getButton(Word word, Question question) {

        CallbackProto.Payload callback = CallbackProto.Payload.newBuilder()
                .setCommand(CallbackNames.CHECK_ANSWER.getTitle())
                .setRightAnswer(question.getRightAnswerWord().getWord())
                .setSelectedAnswerId(word.getId())
                .setRightAnswerId(question.getRightAnswerWord().getId())
                .build();

        String callbackData = callbackQueryWrapper.encode(callback);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(word.getWord());
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    public ReplyKeyboard getConfigKeyboard() {
        KeyboardRow groups = new KeyboardRow();
        groups.add(new KeyboardButton(ButtonNames.SELECT_GROUPS.getTitle()));
        groups.add(new KeyboardButton(ButtonNames.RETURN_TO_MAIN_PAGE.getTitle()));

        return getKeyboardFromRows(List.of(groups));
    }

    public ReplyKeyboard getGroupSelectionKeyboard(UserPref prefs, Page<WordRelationGroup> page) {

        Long selectedGroupId = Optional.ofNullable(prefs.getSelectedGroup())
                .map(WordRelationGroup::getId)
                .orElse(-1L);

        List<InlineKeyboardButton> buttons = page.stream()
                .map(item -> {

                    CallbackProto.Payload payload = CallbackProto.Payload.newBuilder()
                            .setCommand(CallbackNames.SET_GROUP.getTitle())
                            .setNewGroupId(item.getId())
                            .build();
                    String callback = callbackQueryWrapper.encode(payload);

                    String text = item.getTitle();
                    InlineKeyboardButton btn = new InlineKeyboardButton(text);
                    btn.setCallbackData(callback);
                    if (item.getId().equals(selectedGroupId)) {
                        btn.setText("*" + text);
                    }
                    return btn;
                })
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        int rowSize = 3;
        for (int i = 0; i < buttons.size(); i = i + rowSize) {
            List<InlineKeyboardButton> row = buttons.subList(i, Math.min(i + rowSize, buttons.size()));
            rows.add(row);
        }

        return new InlineKeyboardMarkup(rows);
    }

    private ReplyKeyboardMarkup getKeyboardFromRows(List<KeyboardRow> rows) {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
