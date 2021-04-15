package com.eduardovecino.boardgame.annotation;

import com.eduardovecino.boardgame.constants.GamesEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface GameImpl {

    /**
     * Annotates the template name of GameImpl
     *
     * @return
     */
    GamesEnum gameName();

}
