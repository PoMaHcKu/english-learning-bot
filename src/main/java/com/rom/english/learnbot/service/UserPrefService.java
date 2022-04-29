package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.UserPrefRepo;
import com.rom.english.learnbot.model.User;
import com.rom.english.learnbot.model.UserPref;
import com.rom.english.learnbot.model.WordRelationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPrefService {

    private final UserPrefRepo userPrefRepo;

    @Transactional
    public UserPref getByUserOrCreate(User user) {
        return userPrefRepo.findByUserId(user.getId())
                .orElseGet(() -> userPrefRepo.save(UserPref.builder().user(user).build()));
    }

    @Transactional
    public void setSelectedWordGroup(UserPref pref, WordRelationGroup group) {
        pref.setSelectedGroup(group);
        userPrefRepo.save(pref);
    }

}
