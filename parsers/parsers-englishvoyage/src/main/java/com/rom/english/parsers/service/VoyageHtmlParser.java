package com.rom.english.parsers.service;

import com.rom.english.parsers.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

public class VoyageHtmlParser implements HtmlParser {

    public List<WordReference> parseDoc(Document document) {

        String json = ((TextNode) document.childNodes().get(0)).getWholeText();

        JSONArray words = new JSONArray(json);
        List<JSONObject> jsonWords = new ArrayList<>();


        List<Word> englishWords = new ArrayList<>();
        List<Word> russianWords = new ArrayList<>();

        for (int i = 0; i < words.length(); i++) {
            JSONObject word = (JSONObject) words.get(i);
            jsonWords.add(word);
        }

        jsonWords.forEach(ow -> {
            int speechPartNum = ow.getInt("PartOfSpeech");
            SpeechPart speechPart = mapPartSpeech(speechPartNum);
            Word enWord = Word.builder()
                    .language(Language.EN)
                    .speechParts(List.of(speechPart))
                    .word(ow.getString("Word"))
                    .build();
            Word ruWord = Word.builder()
                    .language(Language.RU)
                    .speechParts(List.of(speechPart))
                    .word(ow.getString("Translation"))
                    .build();
            englishWords.add(enWord);
            russianWords.add(ruWord);
        });


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

    private SpeechPart mapPartSpeech(int partSpeech) {
        return switch (partSpeech) {
            case 1 -> new SpeechPart(SpeechPartNames.VERB_PRESENT);
            case 2 -> new SpeechPart(SpeechPartNames.NOUN);
            case 4 -> new SpeechPart(SpeechPartNames.ADJECTIVE);
            case 6 -> new SpeechPart(SpeechPartNames.NUMERIC);
            case 7 -> new SpeechPart(SpeechPartNames.ADVERB);
            case 0 -> new SpeechPart(SpeechPartNames.OTHER);
            default -> throw new RuntimeException("Unknown part speech - " + partSpeech);
        };
    }
}
