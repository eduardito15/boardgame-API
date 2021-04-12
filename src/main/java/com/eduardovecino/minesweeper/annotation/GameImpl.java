package com.eduardovecino.minesweeper.annotation;

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
    public String gameName();

}
