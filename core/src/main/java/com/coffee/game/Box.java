package com.coffee.game;

import static com.coffee.shared.DrawRoundedRectKt.drawRoundedRect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class Box {

    private final SpriteBatch batch;
    private final ShapeRenderer shape;
    private final GlyphLayout layout;
    private final BitmapFont font;


    private int value;
    private static final Color[] colors = {
        new Color(0xEEE4DAFF),
        new Color(0xEDE0C8FF),
        new Color(0xF2B179FF),
        new Color(0xF59563FF),
        new Color(0xF67C5FFF),
        new Color(0xF65E3BFF),
        new Color(0xEDCF72FF),
        new Color(0xEDCC61FF),
        new Color(0xEDC850FF),
        new Color(0xEDC53FFF),
        new Color(0xEDC22EFF),
        new Color(0x3C3A32FF),
        new Color(0x3C3A32FF)
    };

    private final Rectangle bounds;
    private float scale;

    public Box(float size) {
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.layout = new GlyphLayout();
        this.font = new BitmapFont(Gdx.files.internal("Rowdies/Rowdies.fnt"));
        this.bounds = new Rectangle(0, 0, size, size);
        this.font.setColor(new Color(0x786e64ff));
        setScale(0.5f);
        plusValue(2);
    }

    public void plusValue(int value) {
        this.value += value;
        if(Math.sqrt(this.value) > 3f) {
            this.font.setColor(Color.WHITE);
        }
    }

    public void setValue(int value) {
        this.value = value;
        if(Math.sqrt(this.value) > 2f) {
            this.font.setColor(Color.WHITE);
        }
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

        float x = bounds.x + Game.getGrid().getBounds().x;
        float y = bounds.y + Game.getGrid().getBounds().y;

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(colors[indexColor]);
        float width = bounds.width*scale;
        float height = bounds.width*scale;
        float xRec = x + (bounds.width - width)/2;
        float yRec = y + (bounds.height - height)/2;
        drawRoundedRect(shape, xRec, yRec, width, height, width*0.1f);
        shape.end();

        String text = String.valueOf(value);

        float fontScale = scale;
        layout.setText(font, text);
        float wF = layout.width;
        float hF = layout.height;
        while(wF > ((bounds.width*scale)*.8f)) {
            fontScale -= .01f;
            font.getData().setScale(fontScale);
            layout.setText(font, text);
            wF = layout.width;
        }
        batch.begin();
        font.draw(batch, text, x + bounds.getWidth()/2 - wF/2, y + bounds.getHeight()/2 + hF/2);
        batch.end();

    }


    public void dispose() {
        shape.dispose();
        batch.dispose();
        font.dispose();
    }

}
