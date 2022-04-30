package com.rom.english.parsers.service;

import com.rom.english.parsers.model.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExcelParser implements Parser<XSSFWorkbook> {


    @Override
    public List<WordReference> parse(XSSFWorkbook source) {

        XSSFSheet sheet = source.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();

        List<Word> englishWords = new ArrayList<>();
        List<Word> russianWords = new ArrayList<>();

        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            String enWord = Optional.ofNullable(row.getCell(0))
                    .map(XSSFCell::getStringCellValue)
                    .orElse(null);

            String partOfSpeech = Optional.ofNullable(row.getCell(1))
                    .map(XSSFCell::getStringCellValue)
                    .orElse(null);
            String ruWord = Optional.ofNullable(row.getCell(2))
                    .map(XSSFCell::getStringCellValue)
                    .orElse(null);

            if (enWord == null || ruWord == null || partOfSpeech == null) {
                continue;
            }

            SpeechPart speechPart = mapPartSpeech(partOfSpeech);

            Word russian = Word.builder()
                    .word(ruWord)
                    .language(Language.RU)
                    .speechParts(List.of(speechPart))
                    .build();

            Word english = Word.builder()
                    .word(enWord)
                    .language(Language.EN)
                    .speechParts(List.of(speechPart))
                    .build();

            russianWords.add(russian);
            englishWords.add(english);
        }

        if (russianWords.size() != englishWords.size()) {
            String errorMessage = String.format(
                    "Invalid parsing. Unequals sizes of collections: English - %d, Russian - %d",
                    englishWords.size(), russianWords.size());
            throw new RuntimeException(errorMessage);
        }

        List<WordReference> references = new ArrayList<>();

        for (int i = 0; i < englishWords.size(); i++) {
            Word enWord = englishWords.get(i);
            Word ruWord = russianWords.get(i);
            WordReference reference = WordReference.builder()
                    .sourceWord(enWord)
                    .targetWord(ruWord)
                    .build();
            references.add(reference);
        }

        return references;
    }

    private SpeechPart mapPartSpeech(String partSpeech) {
        return switch (partSpeech) {
            case "VERB" -> new SpeechPart(SpeechPartNames.VERB_PRESENT);
            case "PHRASE", "OTHER" -> new SpeechPart(SpeechPartNames.OTHER);
            case "NOUN" -> new SpeechPart(SpeechPartNames.NOUN);
            case "ADVERB" -> new SpeechPart(SpeechPartNames.ADVERB);
            default -> throw new RuntimeException("Unknown part speech - " + partSpeech);
        };
    }
}
