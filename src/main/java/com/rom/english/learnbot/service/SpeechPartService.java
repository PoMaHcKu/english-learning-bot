package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.SpeechPartRepo;
import com.rom.english.learnbot.model.SpeechPart;
import com.rom.english.learnbot.model.SpeechPartNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpeechPartService {

    private final SpeechPartRepo speechPartRepo;

    public SpeechPart getByName(SpeechPartNames speechPartName) {
        return speechPartRepo.findByName(speechPartName);
    }
}
