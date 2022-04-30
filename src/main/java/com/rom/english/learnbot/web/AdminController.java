package com.rom.english.learnbot.web;

import com.rom.english.learnbot.model.WordReference;
import com.rom.english.learnbot.model.WordRelationGroup;
import com.rom.english.learnbot.service.WordReferenceService;
import com.rom.english.learnbot.service.WordRelationGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WordReferenceService wordReferenceService;
    private final WordRelationGroupService wordRelationGroupService;

    @PostMapping("upload-dictionary-item")
    public ResponseEntity<Map<String, Long>> createDictionaryItem(@RequestBody WordReference wordReference,
                                                                  @RequestParam(required = false) String groupTitle) {
        wordReference = wordReferenceService.createNewReference(wordReference);

        if (groupTitle != null) {
            WordRelationGroup group = wordRelationGroupService.getOrCreate(groupTitle);
            wordRelationGroupService.addWordToGroup(group, wordReference);
        }

        return ResponseEntity.ok(Map.of("id", wordReference.getId()));
    }

}
