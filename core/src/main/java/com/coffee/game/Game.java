package com.coffee.game;

import com.coffee.Activity;

public class Game implements Activity {

    private final int width, height;
    private final Grid grid;

    private static final int scale;

    static {
        scale = 10;
    }

    public static int SCALE() {
        return scale;
    }

    public static int SIZE() {
        return 16*scale;
    }

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Grid(width, height);
    }

    @Override
    public void tick() {
        grid.tick();
    }

    @Override
    public void render() {
        grid.render();
    }

    @Override
    public void dispose() {

    }

}
