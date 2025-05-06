package com.coffee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.coffee.game.Game;
import com.coffee.input.Touch;
import com.coffee.menu.Menu;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Engine extends ApplicationAdapter {


    private static OrthographicCamera camera;
    private static Activity activity;
    public static Random rand;
    private static int scale;

    private static Touch touch;

    public static int SCALE() {
        return scale;
    }

    public static void setSCALE(int scale) {
        Engine.scale = scale;
    }

    public static int SIZE() {
        return 16*scale;
    }

    static {
        rand = new Random();
    }

    @Override
    public void create() {
        setSCALE(1);
        this.touch = new Touch();
        Engine.camera = new OrthographicCamera();
        Engine.camera.setToOrtho(false, Engine.getWidth(), Engine.getHeight());
        setActivity(new Menu());
    }

    public static OrthographicCamera getCam() {
        return Engine.camera;
    }

    public static void setActivity(Activity activity) {
        if(Engine.activity != null) {
            Engine.activity.dispose();
        }
        Engine.activity = activity;
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

    public static Touch getInputs() {
        return Engine.touch;
    }

    private void tick() {
        touch.tick();
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
