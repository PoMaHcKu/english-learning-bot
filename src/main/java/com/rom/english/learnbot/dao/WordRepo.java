package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepo extends CrudRepository<Word, Long> {

    Optional<Word> findByWord(String value);

    @Query(value = "select * from words where language = :lang and id not in :notIn order by random() limit 1", nativeQuery = true)
    Word getRandomWord(@Param("lang") String language, @Param("notIn") Long... notIn);
}
