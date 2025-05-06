package com.coffee.game;

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
        if(!isEmpty()) {
            Rectangle curP = this.content.getBounds();
            float fluid = 2;
            float nx = ((bounds.x - curP.x) / fluid);
            float ny = ((bounds.y - curP.y) / fluid);

            float x = Math.abs(nx) < 1f ? bounds.x : curP.x + nx;
            float y = Math.abs(ny) < 1f ? bounds.y : curP.y + ny;
            this.content.setPosition(x, y);
            if(content.getScale() != 1d && done()) {
                float curScale = content().getScale();
                float def = ((1 - content.getScale())/2) * delta*20;
                if(def <= 0.1)
                    content.setScale(1);
                content.setScale(curScale + def);
            }
        }
    }

    public void render() {
        float x = bounds.x + Game.getGrid().getBounds().x;
        float y = bounds.y + Game.getGrid().getBounds().y;
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setProjectionMatrix(Game.getCam().combined);
        shape.setColor(slotColor);
        shape.rect(x, y, bounds.getWidth(), bounds.getHeight());
        shape.end();
    }

    public void dispose() {
        shape.dispose();
    }

}
