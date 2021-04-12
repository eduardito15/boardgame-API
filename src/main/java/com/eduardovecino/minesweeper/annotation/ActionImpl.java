package com.eduardovecino.minesweeper.annotation;

import com.eduardovecino.minesweeper.constants.ActionsEnum;

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
    public String gameName();

    /**
     * Annotates the template action name of ActionImpl
     *
     * @return
     */
    public ActionsEnum action();
}
