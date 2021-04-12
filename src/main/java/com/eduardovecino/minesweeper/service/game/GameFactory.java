package com.eduardovecino.minesweeper.service.game;

import com.eduardovecino.minesweeper.annotation.GameImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFactory.class);

    private ConfigurableApplicationContext context;
    private Map<String, GameService> games;

    public GameFactory(ApplicationContext context) {
        this.context = (ConfigurableApplicationContext) context;
        this.games = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(GameImpl.class);
        beansWithAnnotation.keySet().forEach(lck -> {
            Object bean = beansWithAnnotation.get(lck);
            GameImpl annotation = bean.getClass().getAnnotation(GameImpl.class);
            games.put(annotation.gameName(), (GameService) bean);
            LOGGER.info("Add game impl for {}", annotation.gameName());
        });
    }

    public GameService getInstance(String gameName) {
        return games.get(gameName);
    }
}
