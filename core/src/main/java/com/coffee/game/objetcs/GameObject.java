package com.coffee.game.objetcs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObject {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    public GameObject() {

    }

    public abstract void tick();

    public abstract void render();

    public abstract void dispose();

}
