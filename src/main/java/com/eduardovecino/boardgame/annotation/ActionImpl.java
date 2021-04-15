package com.eduardovecino.boardgame.annotation;

import com.eduardovecino.boardgame.constants.ActionsEnum;
import com.eduardovecino.boardgame.constants.GamesEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ActionImpl {

    /**
     * Annotates the template game name of ActionImpl
     *
     * @return
     */
    GamesEnum gameName();

    /**
     * Annotates the template action name of ActionImpl
     *
     * @return
     */
    ActionsEnum action();
}
