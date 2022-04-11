package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.SpeechPart;
import com.rom.english.learnbot.model.SpeechPartNames;
import org.springframework.data.repository.CrudRepository;

public interface SpeechPartRepo extends CrudRepository<SpeechPart, Long> {

    SpeechPart findByName(SpeechPartNames speechPartName);
}
