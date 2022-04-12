package com.rom.english.learnbot.web;

import com.rom.english.learnbot.model.WordReference;
import com.rom.english.learnbot.service.WordReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WordReferenceService wordReferenceService;

    @PostMapping("upload-dictionary-item")
    public ResponseEntity<Map<String, Long>> createDictionaryItem(@RequestBody WordReference wordReference) {
        Long newReference = wordReferenceService.createNewReference(wordReference);
        return ResponseEntity.ok(Map.of("id", newReference));
    }

}
