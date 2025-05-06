package com.coffee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.coffee.game.Game;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Engine extends ApplicationAdapter {


    private static Activity activity;
    public static Random rand;

    static {
        rand = new Random();
    }

    @Override
    public void create() {
        activity = new Game(4, 4);
    }

    public static Activity getActivity() {
        return Engine.activity;
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
        activity.tick();
    }

    @Override
    public void render() {
            tick();
        Gdx.gl.glClearColor(251, 248, 239, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        activity.render();
    }

    @Override
    public void dispose() {
        activity.dispose();
    }
}
