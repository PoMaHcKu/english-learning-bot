package com.rom.english.learnbot.live;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Component
public class HerokuLiveHolder {

    private static final String PING_URL = "/ping";

    private final RestTemplate restTemplate;

    @Scheduled(fixedDelay = 25, timeUnit = TimeUnit.MINUTES)
    private void pingMyself() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(PING_URL, String.class);
        if (forEntity.hasBody()) {
            String body = forEntity.getBody();
            log.info(body);
        }
    }

}
