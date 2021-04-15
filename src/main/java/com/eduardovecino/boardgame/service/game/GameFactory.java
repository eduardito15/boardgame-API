package com.eduardovecino.boardgame.service.game;

import com.eduardovecino.boardgame.annotation.GameImpl;
import com.eduardovecino.boardgame.constants.GamesEnum;
import com.eduardovecino.boardgame.constants.LogsMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

/**
 * Game Factory is responsible for create games implementations.
 * Search beans with annotation @GameImpl and add to map by game.
 */
@Service
public class GameFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFactory.class);

    private ConfigurableApplicationContext context;
    private EnumMap<GamesEnum, GameService> games;

    public GameFactory(ApplicationContext context) {
        this.context = (ConfigurableApplicationContext) context;
        this.games = new EnumMap<>(GamesEnum.class);
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(GameImpl.class);
        beansWithAnnotation.keySet().forEach(lck -> {
            Object bean = beansWithAnnotation.get(lck);
            GameImpl annotation = bean.getClass().getAnnotation(GameImpl.class);
            games.put(annotation.gameName(), (GameService) bean);
            LOGGER.info(LogsMessages.ADD_GAME_IMPL_FOR, annotation.gameName());
        });
    }

    public GameService getInstance(GamesEnum gameName) {
        return games.get(gameName);
    }
}
