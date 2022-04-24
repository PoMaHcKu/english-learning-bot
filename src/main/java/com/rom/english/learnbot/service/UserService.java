package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.UserRepo;
import com.rom.english.learnbot.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public User getOrCreate(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be null");
        }
        return userRepo.findById(user.getId())
                .orElseGet(() -> userRepo.save(user));
    }

}
