package com.rom.english.learnbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bot_user_pref")
public class UserPref {

    @Id
    @GeneratedValue(generator = "bot_user_pref_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "words_group_id")
    private WordRelationGroup selectedGroup;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
