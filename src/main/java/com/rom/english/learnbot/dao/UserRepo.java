package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
