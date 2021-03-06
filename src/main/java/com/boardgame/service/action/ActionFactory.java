package com.boardgame.service.action;

import com.boardgame.constants.ActionsEnum;
import com.boardgame.annotation.ActionImpl;
import com.boardgame.constants.GamesEnum;
import com.boardgame.constants.LogsMessages;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Action Factory is responsible for create actions implementations.
 * Search beans with annotation @ActionImpl and add to map by game and action.
 */
@Service
public class ActionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionFactory.class);

    private ConfigurableApplicationContext context;
    private Map<Pair<GamesEnum, ActionsEnum>, ActionService> actions;

    public ActionFactory(ApplicationContext context) {
        this.context = (ConfigurableApplicationContext) context;
        this.actions = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(ActionImpl.class);
        beansWithAnnotation.keySet().forEach(lck -> {
            Object bean = beansWithAnnotation.get(lck);
            ActionImpl annotation = bean.getClass().getAnnotation(ActionImpl.class);
            actions.put(Pair.of(annotation.gameName(), annotation.action()), (ActionService) bean);
            LOGGER.info(LogsMessages.ADD_ACTION_IMPL_FOR, annotation.action(), annotation.gameName());
        });
    }

    public ActionService getInstance(GamesEnum gameName, ActionsEnum action) {
        return actions.get(Pair.of(gameName, action));
    }
}
