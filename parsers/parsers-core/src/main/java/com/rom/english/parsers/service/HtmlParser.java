package com.rom.english.parsers.service;

import com.rom.english.parsers.model.WordReference;
import org.jsoup.nodes.Document;

import java.util.List;

public interface HtmlParser {

    List<WordReference> parseDoc(Document document);
}
