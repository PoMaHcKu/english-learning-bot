package com.rom.english.parsers.service;

public interface Uploader<T, R> {

    T upload(R source);

}
