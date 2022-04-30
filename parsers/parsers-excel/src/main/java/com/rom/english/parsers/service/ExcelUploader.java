package com.rom.english.parsers.service;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExcelUploader implements Uploader<XSSFWorkbook, String> {

    @SneakyThrows
    @Override
    public XSSFWorkbook upload(String source) {
        return new XSSFWorkbook(source);
    }
}
