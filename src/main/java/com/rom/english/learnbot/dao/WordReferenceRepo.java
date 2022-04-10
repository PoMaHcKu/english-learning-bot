package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.Language;
import com.rom.english.learnbot.model.WordReference;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordReferenceRepo extends CrudRepository<WordReference, Long> {

    List<WordReference> findAllBySourceWord_Id(Long sourceWordId);

    List<WordReference> findAllBySourceWord_Word(String word);

    Optional<WordReference> findBySourceWord_WordAndTargetWord_Word(String sourceWord, String targetWord);

    @Query(value = "select * from word_relation order by random() limit 1", nativeQuery = true)
    WordReference getRandomWordReference(String language);
}