package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.UserPref;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserPrefRepo extends CrudRepository<UserPref, Long> {

    Optional<UserPref> findByUserId(long userId);
}
