package com.coffee.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Engine;


public class Box {

    private final SpriteBatch batch;
    private final ShapeRenderer shape;
    private GlyphLayout layout;
    private BitmapFont font;


    private int value;
    private static final Color[] colors = {
        new Color(0xFFF5CCFF),
        new Color(0xFFE0AAFF),
        new Color(0xFFB877FF),
        new Color(0xFF8F44FF),
        new Color(0xFF6611FF),
        new Color(0xFF3300FF),
        new Color(0xFF1900FF),
        new Color(0xFF0000FF),
        new Color(0xE00000FF),
        new Color(0xC00000FF),
        new Color(0xA00000FF),
        new Color(0x800000FF),
        new Color(0x600000FF),
        new Color(0x400000FF),
        new Color(0x200000FF),
        new Color(0x000000FF)
    };

    private final Rectangle bounds;
    private float scale;

    public Box(float size) {
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.layout = new GlyphLayout();
        this.font = new BitmapFont(Gdx.files.internal("Rowdies/Rowdies.fnt"));
        this.bounds = new Rectangle(0, 0, size, size);
        setScale(0.5f);
        plusValue(2);
    }

    public void plusValue(int value) {
        this.value += value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        return this.value == ((Box)obj).value;
    }

    public void setPosition(float x, float y) {
        this.bounds.setPosition(x, y);
    }

    public void render() {
        int indexColor = (int) Math.sqrt(this.value) - 1;
        if (indexColor >= colors.length)
            indexColor = colors.length - 1;
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(colors[indexColor]);
        float width = bounds.width*scale;
        float height = bounds.width*scale;
        float x = bounds.x + (bounds.width - width)/2;
        float y = bounds.y + (bounds.height - height)/2;
        shape.rect(x, y, width, height);
        shape.end();

        String text = String.valueOf(value);
        layout.setText(font, text);
        float wF = layout.width;
        float hF = layout.height;
        if(indexColor < 2)
            font.setColor(Color.BLACK);
        else
            font.setColor(Color.WHITE);
        batch.begin();
        font.draw(batch, text, bounds.getX() + bounds.getWidth()/2 - wF/2, bounds.getY() + bounds.getHeight()/2 + hF/2);
        batch.end();

    }


    public void dispose() {
        shape.dispose();
    }

}
