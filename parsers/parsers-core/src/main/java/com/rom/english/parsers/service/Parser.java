package com.rom.english.parsers.service;

import com.rom.english.parsers.model.WordReference;

import java.util.List;

public interface Parser<R> {

    List<WordReference> parse(R source);

}
