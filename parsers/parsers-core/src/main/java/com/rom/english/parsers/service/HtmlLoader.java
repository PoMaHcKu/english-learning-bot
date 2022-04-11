package com.rom.english.parsers.service;

import org.jsoup.nodes.Document;

public interface HtmlLoader {

    Document loadDocument(String url);

}
