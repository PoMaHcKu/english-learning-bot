package com.rom.english.parsers;

import com.rom.english.parsers.model.WordReference;
import com.rom.english.parsers.service.*;
import org.jsoup.nodes.Document;

import java.util.*;

public class Parser {

    private final HtmlLoader loader = new VoyageHtmlLoader();
    private final HtmlParser parser = new VoyageHtmlParser();
    private final WordReferenceUploader uploader = new DefaultWordReferenceUploader();

    public static void main(String[] args) {

        Parser parser = new Parser();
        parser.startParsing();

    }

    public void startParsing() {
        String sourceURL = "https://englishvoyage.com/api/webapi/flashcardset/";
        String sectionUrl = "/flashcard";
        int minPage = 1;
        int maxPage = 38;

        for (int i = minPage; i <= maxPage; i++) {
            String url = String.format("%s%d%s", sourceURL, i, sectionUrl);
            Document document = loader.loadDocument(url);
            if (document == null) {
                continue;
            }
            List<WordReference> wordReferences = parser.parseDoc(document);
            wordReferences.forEach(uploader::uploadWordReferences);
        }
    }

}
