package com.coffee.shared;


import static com.coffee.shared.DrawRoundedRectKt.drawRoundedRect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Engine;

public class Button {
    private final SpriteBatch batch;
    private final ShapeRenderer shape;
    private final GlyphLayout layout;
    private final BitmapFont font;
    private final Color color;
    private final Rectangle bounds;
    private final String value;

    public Button(String value, float x, float y) {
        this.value = value;
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.layout = new GlyphLayout();
        this.font = new BitmapFont(Gdx.files.internal("Rowdies/Rowdies.fnt"));
        this.color = new Color(0xff0000ff);
        int padding = Engine.SCALE()*3;
        layout.setText(font, value);
        float width = layout.width + padding*2;
        float height = layout.height + padding*2;
        this.bounds = new Rectangle(x - width, y - height, width, height);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setPosition(float x, float y) {
        this.bounds.setPosition(x, y);
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public boolean clicked() {
        return Engine.getInputs().isPressed(this.bounds);
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(this.color);
        drawRoundedRect(shape, bounds.x, bounds.y, bounds.width, bounds.height, bounds.width*0.1f);
        shape.end();
        float wF = layout.width;
        float hF = layout.height;
        batch.begin();
        font.draw(batch, value, bounds.x + bounds.getWidth()/2 - wF/2, bounds.y + bounds.getHeight()/2 + hF/2);
        batch.end();
    }

    public void dispose() {
        this.shape.dispose();
    }

}
