package com.coffee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.coffee.game.Game;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Engine extends ApplicationAdapter {

    float tickAccumulator = 0f;
    final float TICK_RATE = 1f / 60f; // 60 Hz


    private Game game;

    public static Random rand;

    static {
        rand = new Random();
    }

    @Override
    public void create() {
        game = new Game(4, 4);
    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public static Graphics getGraphics() {
        return Gdx.graphics;
    }

    public static Input getInputs() {
        return Gdx.input;
    }

    private void tick() {
        game.tick();
    }

    @Override
    public void render() {
            tick();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        game.render();
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
