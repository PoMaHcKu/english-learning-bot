package com.rom.english.parsers.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class VoyageHtmlLoader implements HtmlLoader {

    public Document loadDocument(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return Jsoup.parse(in, "UTF-8", url, Parser.xmlParser());
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
}
