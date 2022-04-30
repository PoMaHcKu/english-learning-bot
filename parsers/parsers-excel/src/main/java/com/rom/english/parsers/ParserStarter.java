package com.rom.english.parsers;

import com.rom.english.parsers.model.WordReference;
import com.rom.english.parsers.service.*;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ParserStarter {

    private final Uploader<XSSFWorkbook, String> loader = new ExcelUploader();
    private final Parser<XSSFWorkbook> parser = new ExcelParser();
    private final WordReferenceUploader uploader = new DefaultWordReferenceUploader();

    public static void main(String[] args) {

        ParserStarter parser = new ParserStarter();
        parser.startParsing();

    }

    @SneakyThrows
    public void startParsing() {
        String path = "/home/roman/IdeaProjects/learn-bot/parsers/parsers-excel/src/main/resources/words.xlsx";

        try (XSSFWorkbook book = loader.upload(path)) {
            if (book == null) {
                return;
            }
            List<WordReference> wordReferences = parser.parse(book);
            wordReferences.forEach(uploader::uploadWordReferences);
        }


    }

}
