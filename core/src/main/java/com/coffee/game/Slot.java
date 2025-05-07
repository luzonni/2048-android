package com.coffee.game;

import static com.coffee.shared.DrawRoundedRectKt.drawRoundedRect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Engine;

public class Slot {

    private ShapeRenderer shape;
    private Color slotColor;
    private final Rectangle bounds;
    private Box content;
    private final static float delta = 1f/60f;


    public Slot(int x, int y, int size) {
        this.shape = new ShapeRenderer();
        this.slotColor = new Color(0xd1c3b6ff);
        this.bounds = new Rectangle(x, y, size, size);
    }

    public boolean isEmpty() {
        return this.content == null;
    }

    public void put(Box box) {
        if(box.equals(content)) {
            box.plusValue(this.content.getValue());
            box.setScale(1.25f);
        }
        if(this.content != null)
            this.content.dispose();
        this.content = box;
    }

    public Box content() {
        return this.content;
    }

    public void putNew() {
        if(isEmpty()) {
            this.content = new Box(this.bounds.width);
            this.content.setPosition(bounds.x, bounds.y);
        }
    }

    public void putNew(int value) {
        if(isEmpty()) {
            this.content = new Box(this.bounds.width);
            this.content.setValue(value);
            this.content.setPosition(bounds.x, bounds.y);
        }
    }

    public void moveTo(Slot slot) {
        this.put(slot.pop());
    }

    public boolean done() {
        if(isEmpty())
            return true;
        float x1 = this.bounds.x;
        float x2 = this.content.getBounds().x;
        float y1 = this.bounds.y;
        float y2 = this.content.getBounds().y;
        return x1 == x2 && y1 == y2;
    }

    public Box pop() {
        Box box = this.content;
        this.content = null;
        return box;
    }

    public boolean compareTo(Slot slot) {
        return !isEmpty() && this.content.equals(slot.content);
    }

    public void tick() {
        if(isEmpty())
            return;
        if(!done()) {
            Rectangle curP = this.content.getBounds();
            float nx = 10 * Engine.SCALE() * Math.signum(bounds.x - curP.x);
            float ny = 10 * Engine.SCALE() * Math.signum(bounds.y - curP.y);

            float x = (Math.abs(bounds.x - curP.x) < Math.abs(nx)) ? bounds.x : curP.x + nx;
            float y = (Math.abs(bounds.y - curP.y) < Math.abs(ny)) ? bounds.y : curP.y + ny;
            this.content.setPosition(x, y);
        }
        if(content.getScale() != 1d && done()) {
            float curScale = content().getScale();
            float def = ((1 - content.getScale())/4) * delta*30;
            if(def <= 0.1)
                content.setScale(1);
            content.setScale(curScale + def);
        }
    }

    public void render() {
        float x = bounds.x + Game.getGrid().getBounds().x;
        float y = bounds.y + Game.getGrid().getBounds().y;
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setProjectionMatrix(Engine.getCam().combined);
        shape.setColor(slotColor);
        drawRoundedRect(shape, x, y, bounds.width, bounds.height, Engine.SIZE()*0.1f);
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }

}
