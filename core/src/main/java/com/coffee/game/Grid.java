package com.coffee.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Engine;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final ShapeRenderer shape;
    private final Color color;
    private final Rectangle bounds;

    private final int width, height;
    private final int padding;
    private final Slot[] grid;
    float startX = 0, startY = 0;
    boolean touched = false;
    private boolean moved;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.padding = 2*Game.SCALE();
        this.shape = new ShapeRenderer();
        this.color = new Color(0xbdada0ff);
        this.bounds = new Rectangle(0, 0, width*(Game.SIZE()+padding)+padding, height*(Game.SIZE()+padding)+padding);
        this.grid = buildGrid();
        spawn();
    }

    private Slot[] buildGrid() {
        Slot[] slots = new Slot[width*height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                slots[x+y*width] = new Slot(padding + (padding+Game.SIZE())*x, padding + (padding+Game.SIZE())*y, Game.SIZE());
            }
        }
        return slots;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    private void spawn() {
        List<Slot> emptySlots = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            if(grid[i].isEmpty()) {
                emptySlots.add(grid[i]);
            }
        }
        emptySlots.get(Engine.rand.nextInt(emptySlots.size())).putNew();
    }

    public Slot getSlot(int x, int y) {
        return grid[x+y*width];
    }

    private boolean allDone() {
        for(int i = 0 ; i < grid.length; i++) {
            Slot slot = grid[i];
            if(!slot.done()) {
                return false;
            }
        }
        return true;
    }

    private void setOnCenterScreen() {
        this.getBounds().setPosition(Engine.getWidth()/2f - getBounds().width/2, Engine.getHeight()/2f - getBounds().height/2);
    }

    public Slot[] getGrid() {
        return this.grid;
    }



    public void setGrid(Integer[] values) {
        for(int i = 0; i < grid.length; i++) {
            Slot slot = grid[i];
            slot.pop();
            if(values[i] != 0) {
                slot.putNew(values[i]);
            }
        }
    }

    public void tick() {
        setOnCenterScreen();
        for(Slot slot : grid) {
            if(slot != null)
                slot.tick();
        }
        if(allDone()) {
            if (Engine.getInputs().justTouched() && !touched) {
                touched = true;
                startX = Engine.getInputs().getX();
                startY = Engine.getInputs().getY();
            } else if (!Engine.getInputs().isTouched() && touched) {
                float deltaX = Engine.getInputs().getX() - startX;
                float deltaY = Engine.getInputs().getY() - startY;
                if(Math.abs(deltaX) <= Game.SIZE()/3f && Math.abs(deltaY) <= Game.SIZE()/3f)
                    return;
                Game.getGame().stackGrid();
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (deltaX > 0) {
                        moved = slide(TypeSlide.Right);
                    } else {
                        moved = slide(TypeSlide.Left);
                    }
                } else {
                    if (deltaY > 0) {
                        moved = slide(TypeSlide.Up);
                    } else {
                        moved = slide(TypeSlide.Down);
                    }
                }
                touched = false;
            }
            if(moved) {
                moved = false;
                spawn();
            }
        }
    }

    private boolean slide(TypeSlide slide) {
        int slides = 0;
        List<Slot> joined = new ArrayList<>();
        if(slide.equals(TypeSlide.Down)) {
            for(int y = height-2; y >= 0; y--) {
                for(int x = 0; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Up)) {
            for(int y = 1; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Left)) {
            for(int y = 0; y < height; y++) {
                for(int x = 1; x < width; x++) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Right)) {
            for(int y = 0; y < height; y++) {
                for(int x = width - 2; x >= 0; x--) {
                    slides += swap(joined, slide, x, y);
                }
            }
        }
        return slides > 0;
    }

    private int swap(List<Slot> joined, TypeSlide dir, int x, int y) {
        Slot curSlot = getSlot(x, y);
        if(curSlot.isEmpty())
            return 0;
        int nextX = x + dir.getNx();
        int nextY = y + dir.getNy();
        while((nextX >= 0 && nextX < width) && (nextY >= 0 && nextY < height)) {
            Slot nextSlot = getSlot(nextX, nextY);
            if(nextSlot.isEmpty()) {
                nextX += dir.getNx();
                nextY += dir.getNy();
            }else if(nextSlot.compareTo(curSlot) && !joined.contains(nextSlot)) {
                nextSlot.moveTo(curSlot);
                joined.add(nextSlot);
                return 1;
            }else {
                break;
            }
        }
        if((nextX-dir.getNx() != x || nextY-dir.getNy() != y) && getSlot(nextX-dir.getNx(), nextY-dir.getNy()).isEmpty()) {
            getSlot(nextX - dir.getNx(), nextY - dir.getNy()).moveTo(curSlot);
            return 1;
        }
        return 0;
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shape.end();
        for(Slot slot : grid) {
            slot.render();
        }
        for(Slot slot : grid) {
            if(!slot.isEmpty())
                slot.content().render();
        }
    }

    public void dispose() {
        for(Slot slot : grid) {
            slot.content().dispose();
            slot.dispose();
        }
        shape.dispose();
    }
}
